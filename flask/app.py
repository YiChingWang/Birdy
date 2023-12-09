from ImageCaptioningModel import ImageCaptioningModel
from getloader import get_loader
import torchvision.transforms as transforms
from google.cloud import texttospeech
from flask import Flask, jsonify, render_template, request

app = Flask(__name__)

# 初始化词汇表
transform = transforms.Compose([
    transforms.Resize((224, 224)),
    transforms.ToTensor(),
])
loader, dataset = get_loader(
    "/Users/olliewang/Desktop/Birdy/flask/flickr8k/Images",
    "/Users/olliewang/Desktop/Birdy/flask/flickr8k/captions.txt",
    transform=transform
)

vocab = dataset.vocab

# 创建模型实例
model_path = '/Users/olliewang/Desktop/Birdy/models/my_checkpoint.pth_backup.tar'
image_captioning_model = ImageCaptioningModel(model_path, vocab)

# creat route


@app.route('/')
def home():
    return render_template("index.html")


@app.route('/generate_caption', methods=['POST'])
def generate_caption():
    file = request.files['image']
    if not file:
        return jsonify({'error': 'No file uploaded'}), 400

    # 使用 ImageCaptioningModel 类的实例处理图片
    image_tensor = image_captioning_model.process_image(file)

    try:
        # 使用 ImageCaptioningModel 类的实例生成字幕
        caption = image_captioning_model.generate_caption(image_tensor)
        print("Generated Caption:", caption)  # 打印生成的字幕

        # 将生成的标注转换为语音
        audio_content = text_to_speech(caption)

        # 将音频内容保存为文件
        audio_file_path = "static/caption_audio.mp3"
        with open(audio_file_path, "wb") as out:
            out.write(audio_content)

        # 返回标注文本和音频文件的路径
        return jsonify({'caption': caption, 'audio_path': audio_file_path})
    except Exception as e:
        return jsonify({'error': str(e)}), 500


# tts
# 初始化Google Text-to-Speech客戶端
client = texttospeech.TextToSpeechClient()


def text_to_speech(text):
    synthesis_input = texttospeech.SynthesisInput(text=text)

    # 選擇語言和語音
    voice = texttospeech.VoiceSelectionParams(
        language_code="en-US",
        ssml_gender=texttospeech.SsmlVoiceGender.NEUTRAL
    )

    # 選擇音頻配置
    audio_config = texttospeech.AudioConfig(
        audio_encoding=texttospeech.AudioEncoding.MP3
    )

    # 進行文字到語音轉換
    response = client.synthesize_speech(
        input=synthesis_input,
        voice=voice,
        audio_config=audio_config
    )

    # 返回音頻內容
    return response.audio_content


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5020, debug=False)  # 或者您选择的任何主机和端口
