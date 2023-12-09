from torchvision.transforms import Compose, Resize, ToTensor, Normalize
from getloader import get_loader
from ImageCaptioningModel import ImageCaptioningModel


# pic transform process
transform = Compose([
    Resize((224, 224)),  # Resize the image to the size the model expects
    ToTensor(),  # Convert the image to a Tensor
    # Normalize with ImageNet's mean and std
    Normalize(mean=[0.485, 0.456, 0.406], std=[0.229, 0.224, 0.225]),
])

# 使用 get_loader 获取数据加载器和数据集
train_loader, dataset = get_loader(
    root_folder="/Users/olliewang/Desktop/Birdy/flask/flickr8k/Images",
    annotation_file="/Users/olliewang/Desktop/Birdy/flask/flickr8k/captions.txt",
    transform=transform,  # 替换为实际的转换方式
    num_workers=2,
)

# 从数据集中获取词汇表
vocab = dataset.vocab
print("Is itos in vocab:", hasattr(vocab, 'itos'))

# 创建模型实例
model_path = '/Users/olliewang/Desktop/Birdy/models/my_checkpoint.pth_backup.tar'
image_captioning_model = ImageCaptioningModel(model_path, vocab)

# 处理图像并生成字幕
image_file = '/Users/olliewang/Desktop/Birdy/flask/flickr8k/Images/69189650_6687da7280.jpg'
image_tensor = image_captioning_model.process_image(image_file)
caption = image_captioning_model.generate_caption(image_tensor)
