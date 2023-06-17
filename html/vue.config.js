const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
    transpileDependencies: true,
    // publicPath: './query/images/dist', // 基本路径
    publicPath: './', // 基本路径
    outputDir: './dist', // 构建时的输出目录
    assetsDir: './assets', // 放置静态资源的目录
    indexPath: 'index.html', // html 的输出路径
    filenameHashing: true, // 文件名哈希值
    lintOnSave: false, // 是否在保存的时候使用 `eslint-loader` 进行检查。
    //调整内部的 webpack 配置
    configureWebpack: () => {},
    chainWebpack: () => {},
    devServer: {
        port: 8080,
        proxy: {
            '/chat': {
                target: 'http://127.0.0.1:8081',
                pathRewrite: { '/chat': '/chat' },
                // ws: true, //用于支持websocket,默认值为true
                // changeOrigin: true //用于控制请求头中的host值,默认值为true,服务器收到的请求头中的host值为target中的值
            }
        }
    },
})