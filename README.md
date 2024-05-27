# MvvmFrameworkDemo
组件化架构库 --- 后续会陆续添加一些的工具和借鉴下大佬写的三方库

# SKu
命名规范

# 命名规范
    app - 宿主
    xxxModel - 业务包
    xxxLib - 依赖库
    common - 基础库
    resources - 图片drawable资源库(整个项目使用 避免多图片出现)
    bean - 公用模型库

# 整体结构图
    整体分包图     每个xxxModel都可以单个运行 组件化分层 统一由config.gradle控制
                app 
                / \
        xxxModel   xxModel
          /  \      /
    xxxLib    \    /
               common   
               /   \       \
         xxxLib  resources   bean
# 基础库
common

# 依赖库
xxxLib

# 模块库
xxxModel
