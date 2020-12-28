import Vue from 'vue';
import Router from 'vue-router';

Vue.use(Router);

export default new Router({
    routes: [
        {
            path: '/',
            redirect: '/login'
        },
        {
            path: '/',
            component: () => import(/* webpackChunkName: "home" */ '../components/common/Home.vue'),
            meta: { title: '自述文件' },
            children: [
                {
                    path: '/dashboard',
                    component: () => import(/* webpackChunkName: "dashboard" */ '../components/page/Dashboard.vue'),
                    meta: { title: '系统首页' }
                },
                {
                    path: '/member',
                    component: () => import(/* webpackChunkName: "dashboard" */ '../components/page/member.vue'),
                    meta: { title: '用户管理' }
                },
                {
                    path: '/material-manager',
                    component: () => import(/* webpackChunkName: "dashboard" */ '../components/page/material-manager'),
                    meta: { title: '资料管理' }
                },
                {
                    path: '/homework-manager',
                    component: () => import(/* webpackChunkName: "dashboard" */ '../components/page/homework-manager'),
                    meta: { title: '作业管理' }
                },
                {
                    path: '/forum',
                    component: () => import(/* webpackChunkName: "dashboard" */ '../components/page/forum'),
                    meta: { title: '论坛话题' }
                },
                {
                    path: '/course',
                    component: () => import(/* webpackChunkName: "dashboard" */ '../components/page/course'),
                    meta: { title: '课程管理' }
                },
                {
                    path: '/student-teacher',
                    component: () => import(/* webpackChunkName: "dashboard" */ '../components/page/student-teacher'),
                    meta: { title: '学生管理' }
                },



                {
                    // 权限页面
                    path: '/permission',
                    component: () => import(/* webpackChunkName: "permission" */ '../components/page/Permission.vue'),
                    meta: { title: '权限测试', permission: true }
                },
                {
                    path: '/404',
                    component: () => import(/* webpackChunkName: "404" */ '../components/page/404.vue'),
                    meta: { title: '404' }
                },
                {
                    path: '/403',
                    component: () => import(/* webpackChunkName: "403" */ '../components/page/403.vue'),
                    meta: { title: '403' }
                },
            ]
        },
        {
            path: '/login',
            component: () => import(/* webpackChunkName: "login" */ '../components/page/Login.vue')
        },
        {
            path: '*',
            redirect: '/404'
        }
    ]
});
