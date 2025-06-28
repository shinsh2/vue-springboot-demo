const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  outputDir: '../src/main/resources/static',
  indexPath: '../templates/index.html',
  // outputDir: '../src/main/webapp'
  devServer: {
    port: 8080,
    proxy: {
      '/api': {
        target: 'http://localhost:8080'
      }
    }
  },
  css: {
    sourceMap: true,
    loaderOptions: {
      scss: {
        additionalData: `
          @import "~@/assets/style/_mixins.scss";
          @import "~@/assets/style/_variables.scss";
          @import "~@/assets/style/_mediaQueries.scss";
          @import "~@/assets/style/_svg.scss";
          @import "~@/assets/style/_common.scss";
        `
      }
    }
  }
})
