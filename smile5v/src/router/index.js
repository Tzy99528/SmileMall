import { createRouter } from 'vue-router'

const globalRoutes = []

export const mainRoutes = {}

const router = createRouter({
    routes: globalRoutes.concat(mainRoutes)
})

export default router