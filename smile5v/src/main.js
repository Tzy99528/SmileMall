import { createApp } from 'vue'
import App from './App.vue'
import router from './router/index.js'
import ElementPlus from 'element-plus'
import locale from 'element-plus/es/locale/lang/zh-cn'

const app =createApp(App)

app.use(router)

app.use(ElementPlus, { locale })

app.mount('#app')
