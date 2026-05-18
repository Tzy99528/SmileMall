import axios from 'axios'
import qs from 'qs'
import merge from 'lodash/merge'

// 创建 Axios 实例
const http = axios.create({
    timeout: 1000 * 30,
    withCredentials: true,
    header: {
        'Content-type': 'application/json; charset=utf-8'
    }
})

/**
 * 请求地址处理
 * 基本方法拼接
 */
http.adornUrl = actionName => {
    return import.meta.env.VITE_APP_BASE_API + actionName
}

/**
 * post 请求数据处理
 */
http.adornData = (data = {}, openDefaultdate = true, contentType = 'json') => {
    const defaults = {
        t: Date.now()
    }
    data = openDefaultdate ? merge(defaults, data) : data
    return contentType === 'json' ? JSON.stringify(data) : qs.stringify(data)
}

export default http