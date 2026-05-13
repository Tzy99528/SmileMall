import { createRouter, createWebHistory } from 'vue-router'
import Login from '@/views/common/login/index.vue'
import Layout from '@/layout/main.vue'

const globalRoutes = [
    {
        path: '/404',
        component: () => import('@/views/common/error-page/404.vue'),
        name: '404',
        meta: { title: '404 未找到' }
    },
    {
        path: '/login',
        component: Login,
        name: 'login',
        meta: { title: '登录' }
    }
]

export const mainRoutes = {
    path: '/',
    component: Layout,
    name: 'index',
    redirect: '/login',
    children: [
        {
            path: 'home',
            name: 'home',
            component: () => import('@/views/common/home/index.vue')
        },
        {
            path: 'prodInfo',
            name: 'prodInfo',
            component: () => import('@/views/modules/prod/prodInfo/index.vue')
        }
    ]
}

const router = createRouter({
    history: createWebHistory(),
    scrollBehavior: () => ({ top: 0 }),
    routes: globalRoutes.concat(mainRoutes)
})

export default router