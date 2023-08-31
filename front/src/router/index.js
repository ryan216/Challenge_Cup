import {createRouter, createWebHashHistory, createWebHistory} from "vue-router";
import Index from "../components/Index.vue";
import Index2 from "../components/Index2.vue";
let router = createRouter({
    history: createWebHashHistory(),
    routes: [
        {
            path: "/",
            name: "首页",
            component: Index2,
            redirect: "/index"
        },
        {
            path: "/index",
            name: "智慧隧道",
            component: Index2
        },
        // {
        //     path: "/index2",
        //     name: "index2",
        //     component: Index
        // }
    ]
});
//路由守卫：to:要去的路由,from: 当前路由,next(): 放行
// router.beforeEach((to, from, next) => {
//     if (to.path.startsWith('/login')) {
//         window.localStorage.removeItem('access-token')
//         next()
//     } else if (to.path.startsWith('/index')) {
//         let user = window.localStorage.getItem('access-token');
//         if (!user) {
//             next({path: '/login'})
//         } 
//         next()
//     } else if (to.path.startsWith('/viewLog')) {
//         let user = window.localStorage.getItem('access-token');
//         if (!user) {
//             next({path: '/login'})
//         } 
//         next()
//     } else {
//         next()
//     }
// });
export default router;