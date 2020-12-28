<template>
    <div class="login-wrap">
        <div class="ms-login">
            <div class="ms-title" >师生交流平台</div>
            <el-form :model="param" :rules="rules" ref="login" label-width="0px" class="ms-content">
                <el-form-item prop="username">
                    <el-input v-model="param.account" placeholder="账户">
                        <el-button slot="prepend" icon="el-icon-lx-people"></el-button>
                    </el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input
                        type="password"
                        placeholder="密码"
                        v-model="param.password"
                        @keyup.enter.native="submitForm()"
                    >
                        <el-button slot="prepend" icon="el-icon-lx-lock"></el-button>
                    </el-input>
                </el-form-item>
                <div class="login-btn">
                    <el-button type="primary" @click="submitForm()">登录</el-button>
                </div>
                <p class="login-tips">Tips : 用户名和密码随便填。</p>
            </el-form>
        </div>
    </div>
</template>

<script>
export default {
    data: function() {
        return {
            param: {
                account: '',
                password: '',
            },
            loginParams: {
                account: '',
                password: '',
            },
            rules: {
                account: [{ required: true, message: '请输入账号', trigger: 'blur' }],
                password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
            },
        };
    },
    methods: {
        submitForm() {
            
            if(this.param.password == "123"){
                this.$message.error('登陆失败');
                return
            }
            this.$refs.login.validate(valid => {
                //alert("2")

                let sha256 = require("js-sha256").sha256//这里用的是require方法
                this.loginParams.password = sha256(this.param.password)//使用sha256密码加密
                this.loginParams.account = this.param.account

                if (valid){
                    this.$post("/User/Login",this.loginParams).then(res=>{
                            if(res.resultCode ==='00'){
                                this.$stores.commit('set_token', res.data.token);//登录成功后将token存储在sessionStorage之中
                                sessionStorage.setItem("token",res.data.token);
                                this.$cookies.set('userId', res.data.userId); //登录成功后将mid存储在cookie之中
                                this.$cookies.set('role', res.data.role); //登录成功后将type存储在cookie之中
                                this.$cookies.set('name', res.data.name); //登录成功后将type存储在cookie之中
                                this.$cookies.set('account', res.data.account); //登录成功后将type存储在cookie之中
                                this.$message.success(res.message);
                                this.$axios.defaults.headers.common['x-auth-token'] = res.data.token;
                                if (res.data.role == 1) {
                                    this.$router.push('/Dashboard');
                                }else if (res.data.role == 2) {
                                    this.$router.push('/Dashboard');
                                }else if (res.data.role == 3) {
                                    this.$router.push('/Dashboard');
                                }
                            }else{
                                this.$message.error(res.message);
                            }

                        }
                    ).catch(()=>{
                            this.$message.error('登陆失败');
                            
                            return false;
                        }

                    )
                    // localStorage.setItem('ms_username', this.member.username);

                } else {
                    this.$message.error('请输入账号和密码');
                    console.log('error submit!!');
                    return false;
                }
            });
        },
    },
};
</script>

<style scoped>
.login-wrap {
    position: relative;
    width: 100%;
    height: 100%;
    background-image: url(../../assets/img/login-bg.jpg);
    background-size: 100%;
}
.ms-title {
    width: 100%;
    line-height: 50px;
    text-align: center;
    font-size: 20px;
    color: black;
    border-bottom: 1px solid #ddd;
}
.ms-login {
    position: absolute;
    left: 50%;
    top: 50%;
    width: 350px;
    margin: -190px 0 0 -175px;
    border-radius: 5px;
    background: rgba(255, 255, 255, 0.3);
    overflow: hidden;
}
.ms-content {
    padding: 30px 30px;
}
.login-btn {
    text-align: center;
}
.login-btn button {
    width: 100%;
    height: 36px;
    margin-bottom: 10px;
}
.login-tips {
    font-size: 12px;
    line-height: 30px;
    color: #fff;
}
</style>
