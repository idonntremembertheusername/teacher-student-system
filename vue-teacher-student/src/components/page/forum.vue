<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-warn"></i> 论坛话题</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <el-button type="primary" @click="back" >返回上一层</el-button>
            <h1>{{forum.title}}</h1>
            <div >

                <div v-html="forum.content" style=" font-size: 18px;line-height: 25px;padding: 30px"></div>
            </div>
           <div style="height: 2rem"></div>
            <div style="height: 2rem">
                评论区：
            </div>
            <div v-for="message in messages">
                <el-card class="box-card" shadow="never">
                    <div slot="header" class="clearfix">
                        <span><strong>{{message.authorName}}</strong> {{message.createTime}}</span>
                        <el-button style="float: right;" type="danger" @click="deleteForum(message.messageId)" v-if="message.authorId == userId|| role!=3">删除</el-button>
                    </div>
                    <div >
                        <div v-html="message.content" style=" font-size: 18px;line-height: 25px;padding: 30px"></div>
                    </div>
                </el-card>


                <div style="height: 1rem"></div>
            </div>





            <div style="height: 1rem"></div>
            <quill-editor ref="myTextEditor" v-model="newMessage.content" ></quill-editor>
            <div style="padding: 1rem">
                <el-button type="primary" @click="answer" >答 复</el-button>
            </div>

        </div>




    </div>
</template>

<script>
    //导入富文本编辑器
    import 'quill/dist/quill.core.css';
    import 'quill/dist/quill.snow.css';
    import 'quill/dist/quill.bubble.css';
    import { quillEditor } from 'vue-quill-editor';
    export default {
        data: function(){
            return {
                newMessage:{
                    authorName:"",
                    authorId:"",
                    forumId:"",
                    content:"",
                },
                role:"",
                userId:"",
                messages:[],
                forum:"",
                forumId:"",

            }
        },
        created() {
            this.newMessage.authorName =this.$cookies.get('name')
            this.newMessage.forumId =this.$route.query.forumId
            this.newMessage.authorId =this.$cookies.get('userId')
            this.forumId = this.$route.query.forumId
            this.role = this.$cookies.get("role")
            this.userId =this.$cookies.get('userId')
            this.loadForum()
            this.loadData();
        },
        components: {
            quillEditor
        },
        methods:{
            deleteForum(messageId){
                this.$deleteRequest("/Message?messageId="+messageId).then(res=>{
                    this.$message.success(res.message)
                    this.loadData()
                })
            },
            answer(){
                this.$post("/Message",this.newMessage).then(res=>{
                    this.$message.success(res.message)
                    this.loadData()
                    this.newMessage.content = ""
                })
            },
            loadForum(){
                this.$fetch("Forum/"+this.forumId).then(res=>{
                    this.forum = res.data

                })
            },
            loadData(){
                this.$fetch("/Message?forumId="+this.forumId).then(res=>{
                    this.messages = res.data.records;
                    this.page.total = res.data.total;
                    this.page.pages = res.data.pages;
                    this.query.size = res.data.size;

                })
            },

            back(){
                this.$router.go(-1)
            },
        },
    }
</script>

<style scoped>
h1{
    text-align: center;
    margin: 30px 0;
}
p{
    line-height: 30px;
    margin-bottom: 10px;
    text-indent: 2em;
}
.logout{
    color: #409EFF;
}
</style>
