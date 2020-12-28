import Vue from 'vue';
import App from './App.vue';
import router from './router';
import ElementUI from 'element-ui';
import VueI18n from 'vue-i18n';
import { messages } from './components/common/i18n';
// import 'element-ui/lib/theme-chalk/index.css'; // 默认主题
import './assets/css/theme-green/index.css'; // 浅绿色主题
import './assets/css/icon.css';
import './components/common/directives';
import 'babel-polyfill';
import 'default-passive-events'


import qs from 'qs';
Vue.prototype.qs=qs;
import axios from 'axios'
import {post,fetch,patch,put,deleteRequest,upload,getFile} from './utils/http'
//定义全局变量
Vue.prototype.$axios=axios;
Vue.prototype.$post=post;
Vue.prototype.$fetch=fetch;
Vue.prototype.$patch=patch;
Vue.prototype.$put=put;
Vue.prototype.$deleteRequest=deleteRequest;
Vue.prototype.$upload=upload;
Vue.prototype.$getFile=getFile;


import stores from './utils/store'
Vue.prototype.$stores=stores;

// 第二种方式
import cookies from 'vue-cookies'    // 导入插件
Vue.prototype.$cookies = cookies;    // 直接配置插件原型 $cookies

Vue.use(cookies)
// let token = ;
axios.defaults.headers.common['x-auth-token'] = sessionStorage.getItem("token");




Vue.config.productionTip = false;
Vue.use(VueI18n);
Vue.use(ElementUI, {
    size: 'small'
});
const i18n = new VueI18n({
    locale: 'zh',
    messages
});



//router.beforeEach


// 页面刷新时，重新赋值token
if (sessionStorage.getItem('token')) {
    stores.commit('set_token', sessionStorage.getItem('token'))
}


router.beforeEach((to, from, next) => {
    if (to.matched.some(r => r.meta.requireAuth)) {           //这里的requireAuth为路由中定义的 meta:{requireAuth:true}，意思为：该路由添加该字段，表示进入该路由需要登陆的
    if (sessionStorage.getItem('token')!=null) {
        next();
    }
    else {
        next({
            path: '/login',
            query: {redirect: to.fullPath}
        })
    }
}
else {
    next();
    }
})




// //使用钩子函数对路由进行权限跳转
// router.beforeEach((to, from, next) => {
//
//
//     // if (localStorage.getItem("member")===null||localStorage.getItem("member").length === 0) { // 判断当前用户是否已拉取完user_info信息
//     //     this.$fetch("member/getInfo?mid="+this.$cookies.get('mid')).then(res => { // 拉取user_info
//     //         const member = res.data.member;
//     //         next();//resolve 钩子
//     //     });
//     // }
//
//     document.title = `${to.meta.title} | 综合实践管理系统`;
//
//
//
//
//     //
//     if (cookies.get('mid')!=null||cookies.get('mid')!='') {
//
//
//         // if (to.path === '/login') {
//         //     // if is logged in, redirect to the home page
//         //     next({ path: '/login' })
//         // }
//     //     else {
//     //         // determine whether the user has obtained his permission roles through getInfo
//     //         const hasRoles = localStorage.getItem('member') && localStorage.getItem('member').length > 0
//     //         if (hasRoles) {
//     //             next()
//     //         } else {
//     //             fetch("/member/getInfo?mid="+cookies.get('mid')).then(res=>{
//     //                 console.log(JSON.stringify(res))
//     //                 localStorage.setItem("member",JSON.stringify(res));
//     //                 next();
//     //             })
//     //         }
//     //     }
//     // }
//     // else {
//     //     this.$message.error('请先登陆');
//     //     next({ path: '/login' })
//     // }
//
//
//
//
//
//     // const role = localStorage.getItem('ms_username');
//     // if (!role && to.path !== '/login') {
//     //     next('/login');
//     // } else if (to.meta.permission) {
//     //     // 如果是管理员权限则可进入，这里只是简单的模拟管理员权限而已
//     //     role === 'admin' ? next() : next('/403');
//     // } else {
//     //     // 简单的判断IE10及以下不进入富文本编辑器，该组件不兼容
//     //     if (navigator.userAgent.indexOf('MSIE') > -1 && to.path === '/editor') {
//     //         Vue.prototype.$alert('vue-quill-editor组件不兼容IE10及以下浏览器，请使用更高版本的浏览器查看', '浏览器不兼容通知', {
//     //             confirmButtonText: '确定'
//     //         });
//     //     } else {
//             next();
//     //     }
//     }
// });

new Vue({
    router,
    i18n,
    stores,
    render: h => h(App)
}).$mount('#app');
