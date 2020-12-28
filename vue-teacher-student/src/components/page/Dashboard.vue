<template>
    <div>
        <el-row :gutter="20">
            <el-col :span="8">
                <el-card shadow="hover" style="height:9rem;">
                    <div slot="header" class="clearfix">
                        <span>我的信息</span>
                        <el-button style="float: right" type="primary" @click="updatePassword">修改密码</el-button>
                    </div>
                    当前登录账户为：{{account}}<br>
                    名称：{{userName}}
                    <el-button style="float: right" type="primary" @click="updateName">修改名称</el-button>
                    <br><br>
                </el-card>
                <div style="height: 1rem"></div>
                <el-card shadow="hover" style="height:12rem;">
                    <div slot="header" class="clearfix">
                        <span>论坛详情</span>
                    </div>
                    已发布话题：{{dashboardVo.forumPublish}}<br><br>
                    已回复话题：{{dashboardVo.forumAnswer}}<br><br>
                    未回复话题：{{dashboardVo.forumNoAnswer}}<br><br>
                </el-card>
                <div style="height: 1rem"></div>
                <el-card shadow="hover" style="height:12rem;">
                    <div slot="header" class="clearfix">
                        <span>资料详情</span>
                    </div>
                    已上传课件：{{dashboardVo.material1}}<br><br>
                    已上传作业：{{dashboardVo.material2}}<br><br>
                    已上传答案：{{dashboardVo.material3}}<br><br>
                </el-card>
                <div style="height: 1rem"></div>
                <el-card shadow="hover" style="height:12rem;">
                    <div slot="header" class="clearfix">
                        <span>作业详情</span>
                    </div>
                    已提交作业：{{dashboardVo.homeworkUpload}}<br><br>
                    已评分：{{dashboardVo.homeworkGoal}}<br><br>
                    未评分：{{dashboardVo.homeworkNoGoal}}<br><br>
                </el-card>
            </el-col>
            <el-col :span="16">

                <el-card shadow="hover" style="height: 47rem">
                    <div slot="header" class="clearfix">
                        <span>论坛管理</span>
                    </div>

                    <div class="handle-box">
                        <el-button type="primary" @click="showPublish" v-if="role!=1">发布话题</el-button>
                        &emsp;&emsp;
                        角色：
                        <el-select v-model="query.role" placeholder="请选择" style="padding: 10px">
                            <el-option label="全部" value="" ></el-option>
                            <el-option label="教师" value="2"></el-option>
                            <el-option label="学生" value="3"></el-option>

                        </el-select>
                        &emsp;
                        <el-input v-model="query.title" placeholder="话题" class="handle-input mr10"  style="width: 10rem"></el-input>&emsp;
                        <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
                    </div>
                    <div style="height: 1rem">

                    </div>
                    <el-table
                            :data="forums"
                            border
                            class="table"
                            ref="multipleTable"
                            header-cell-class-name="table-header"
                    >
                        <el-table-column prop="title"  label="标题"></el-table-column>

                        <el-table-column prop="author" label="发布者">
                            <template slot-scope="scope">{{scope.row.author}}</template>
                        </el-table-column>

                        <el-table-column prop="answer" label="回复数">
                            <template slot-scope="scope">{{scope.row.answer}}</template>
                        </el-table-column>


                        <el-table-column prop="createTime" label="创建时间">
                            <template slot-scope="scope">{{scope.row.createTime}}</template>
                        </el-table-column>
                        <el-table-column label="操作" width="222" align="center">
                            <template slot-scope="scope">

                                <el-button  @click="ShowExamine(scope.row.forumId)">查看</el-button>
                                <el-button type="primary" @click="editExamine(scope.row)" v-if="scope.row.authorId==userId||role!=3">编辑</el-button>
                                <el-button type="danger"  @click="handleDelete(scope.row.forumId)" v-if="scope.row.authorId==userId||role!=3">删除</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <div class="pagination">
                        <el-pagination
                                background
                                layout="total,sizes, prev, pager, next,jumper"
                                :current-page="query.current"
                                :page-sizes="[3,8, 10, 12, 14]"
                                :page-size="query.size"
                                :total="page.total"
                                @current-change="currentChange"
                                @size-change="sizeChange"
                        ></el-pagination>
                    </div>

                </el-card>
            </el-col>
        </el-row>


        <!--        发布话题-->
        <el-dialog title="发布话题" :visible.sync="publishVisible" width="70%">
            <el-form ref="form" :model="newForum" label-width="70px">
                <el-form-item label="课程名称">
                    <el-col :span="6">
                        <el-select v-model="newForum.courseId" placeholder="请选择" @change="changeCourseValue">
                            <el-option v-for="course in courses" :label="course.name" :value="course.courseId" ></el-option>
                        </el-select>
                    </el-col>
                </el-form-item>
                <el-form-item label="话题标题">
                    <el-col :span="6">
                        <el-input v-model="newForum.title"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="话题内容" >
                    <quill-editor ref="myTextEditor" v-model="newForum.content"></quill-editor>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="saveEdit">确 定</el-button>
                <el-button @click="publishVisible = false">取 消</el-button>
            </span>
        </el-dialog>
        <!--        编辑话题-->
        <el-dialog title="编辑" :visible.sync="editExamineVisible" width="70%">
            <el-form ref="form" :model="editForum" label-width="70px">
                <el-form-item label="话题标题">
                    <el-col :span="6">
                        <el-input v-model="editForum.title"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="话题内容" >
                    <quill-editor ref="myTextEditor" v-model="editForum.content" ></quill-editor>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="update">修 改</el-button>
                <el-button @click="editExamineVisible = false">取 消</el-button>
            </span>
        </el-dialog>

        <!-- 修改密码弹出框 -->
        <el-dialog title="修改密码" :visible.sync="passwordVisible" width="30%">
            <el-form ref="form" :model="Password" label-width="70px">
                <el-form-item label="旧密码">
                    <el-input v-model="Password.oldPassword"></el-input>
                </el-form-item>
                <el-form-item label="新密码">
                    <el-input v-model="Password.newPassword"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="commitPassword()">确 定</el-button>
                <el-button type="primary" @click="passwordVisible = false">取 消</el-button>
            </span>
        </el-dialog>


        <!-- 修改名称弹出框 -->
        <el-dialog title="修改密码" :visible.sync="changeNameVisible" width="30%">
            <el-form ref="form" :model="newName" label-width="70px">
                <el-form-item label="新名称">
                    <el-input v-model="newName"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="commitName()">确 定</el-button>
                <el-button type="primary" @click="changeNameVisible = false">取 消</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    //导入富文本编辑器
    import 'quill/dist/quill.core.css';
    import 'quill/dist/quill.snow.css';
    import 'quill/dist/quill.bubble.css';
    import { quillEditor } from 'vue-quill-editor';
    export default {
        name: 'dashboard',
        data() {
            return {
                newForum:{
                    content:"",
                    courseId:"",
                    title:"",
                    courseName:"",
                    role:"",
                    author:"",
                    authorId:""
                },
                editForum:{
                    content:"",
                    courseId:"",
                    title:"",
                    courseName:"",
                    role:"",
                    author:"",
                    authorId:""
                },
                courses:[],
                forums:[],
                query: {
                    role: '',
                    title: '',
                    size: 8,
                    current: 1,
                },
                page:{
                    pages:1,
                    total:1,
                },

                dashboardVo:{},
                name:"",
                role:"",
                userId:"",
                newName:"",
                changeNameVisible: false,
                account:"",
                userName:'',
                Password:{
                    oldPassword:"",
                    newPassword:"",
                },
                changePassword:{
                    oldPassword:"",
                    newPassword:"",
                    userId:"",
                },




                doctype:'',
                form:'',

                companyData: [
                    {
                        id: 1,
                        name: "数据库设计中范式的重要性",
                        registTime: '2020 1 10',
                        answer: 5,
                        stage: "大壮",
                        status: 0
                    },
                    {
                        id: 2,
                        name: "tcp的三次握手和四次挥手的重要性",
                        registTime: '2020 1 10',
                        answer: 5,
                        stage: "梁老师",
                        status: 1
                    },
                    {
                        id: 3,
                        name: "jvm调优介绍",
                        registTime: '2020 1 10',
                        answer: 5,
                        stage: "狗蛋",
                        status: 1
                    },
                    {
                        id: 4,
                        name: "mysql存储引擎",
                        registTime: '2020 1 10',
                        answer: 5,
                        stage: "小李",
                        status: 0
                    },

                ],
                passwordVisible: false,
                publishVisible: false,
                editVisible: false,
                editExamineVisible: false,
                examineVisible: false,

            }
        },
        components: {
            quillEditor
        },
        computed: {
            role() {
                return this.name === 'admin' ? '超级管理员' : '普通用户';
            }
        },
        created(){
            this.name = this.$cookies.get("name")
            this.role = this.$cookies.get("role")
            this.userId = this.$cookies.get("userId")
            this.userName = this.$cookies.get("name")
            this.account = this.$cookies.get("account")
            this.loadCountData()
            this.loadData()

        },
        activated(){
            // this.loadCountData()
        },
        watch: {
            'query.role': { // 监视pagination属性的变化
                // deep: true, // deep为true，会监视pagination的属性及属性中的对象属性变化
                handler(val) {
                    this.initPage();
                    // 变化后的回调函数，这里我们再次调用getDataFromServer即可
                    this.loadData();
                }
            },


        },
        methods: {
            // 更新话题
            update() {
                this.$post("/Forum/update",this.editForum).then(res=>{
                    this.$message.success(res.message)
                    this.editExamineVisible = false;
                    this.loadData()
                })
            },
            //修改话题
            editExamine(forum){
                this.editForum = forum
                this.editExamineVisible = true
            },
            // 删除话题
            handleDelete(forumId) {
                // 二次确认删除
                this.$confirm('确定要删除话题吗？', '提示', {
                    type: 'warning'
                })
                    .then(() => {
                        this.$deleteRequest("/Forum?forumId="+forumId).then(res=>{
                            this.$message.success(res.message);
                            this.loadData()
                        })

                    })
                    .catch(() => {});
            },
            // 发布话题
            saveEdit() {
                this.newForum.role = this.role
                this.newForum.author = this.name
                this.newForum.authorId = this.userId
                this.$post("/Forum",this.newForum).then(res=>{
                    this.$message.success(res.message)
                    this.publishVisible = false;
                    this.loadData()
                    this.newForum.content=""
                    this.newForum.title = ""
                })
            },
            //获取lable值
            changeCourseValue(val){
                let obj = {};
                obj = this.courses.find((course)=>{
                    return course.courseId === val;
                });
                this.newForum.courseName = obj.name;
            },
            //查询话题
            handleSearch(){
              this.loadData()
            },
            //初始化分页条件
            initPage(){
                this.query.current=1;
                this.page.pages=1;
                this.page.total=0;
            },
            //页面改变事件
            currentChange(val){
                this.query.current = val;
                this.loadData();
            },
            //页面大小改变事件
            sizeChange(val){
                this.query.size = val;
                this.loadData();
            },


            //初始化数据
            loadCountData(){
                this.$fetch("/User/count?role="+this.role+"&userId="+this.userId).then(res=>{
                    this.dashboardVo = res.data
                })
            },
            //初始化数据
            loadData(){
                this.$fetch("/Forum",this.query).then(res=>{
                    this.forums = res.data.records;
                    this.query.current = res.data.current;
                    this.query.size = res.data.size;
                    this.page.pages = res.data.pages;
                    this.page.total = res.data.total;

                })
            },
            //修改密码弹窗
            updateName(){
                this.changeNameVisible = true
            },
            //提交修改名称
            commitName(){
                this.$patch("/User/ChangeName?userId="+this.$cookies.get("userId")+"&name="+this.newName).then(res=>{
                    this.userName = this.newName
                    this.$cookies.set('name', this.newName); //登录成功后将type存储在cookie之中
                    this.newName = "";
                    this.$message.success(res.message);
                    this.changeNameVisible = false;
                })
            },
            //修改密码弹窗
            updatePassword(){
                this.passwordVisible = true
            },
            //提交修改密码
            commitPassword(){
                this.changePassword.userId = this.$cookies.get("userId");
                let sha256 = require("js-sha256").sha256//这里用的是require方法
                this.changePassword.oldPassword = sha256(this.Password.oldPassword)//使用sha256密码加密
                this.changePassword.newPassword = sha256(this.Password.newPassword)//使用sha256密码加密
                this.Password.oldPassword = ""
                this.Password.newPassword = ""
                this.$patch("/User/ChangePassword?userId="+this.changePassword.userId+"&newPassword="+this.changePassword.newPassword+
                    "&oldPassword="+this.changePassword.oldPassword).then(res=>{

                    if(res.resultCode=="00") {
                        this.$message.success(res.message);
                        this.passwordVisible = false
                    }else{
                        this.$message.error(res.message);
                    }
                })

            },







            showPublish(){
                if(this.role == 2){
                    //获取教师的course信息
                    this.$fetch("/Course/teacher/"+this.$cookies.get("userId")).then(res=>{
                        this.courses = res.data;
                    })
                }
                if(this.role == 3){
                    //获取学生的course信息
                    this.$fetch("/Course/"+this.$cookies.get("userId")).then(res=>{
                        this.courses = res.data;
                    })
                }
                this.publishVisible = true
            },


            ShowExamine(forumId){
                // this.$router.push("/forum")
                this.$router.push("/forum?forumId="+forumId)
            },







            // 上线操作
            handleOnline(index, row) {
                // 二次确认上线
                this.$confirm('确定要上线吗？', '提示', {
                    type: 'warning'
                })
                    .then(() => {
                        this.$message.success('上线成功');
                        this.tableData.splice(index, 1);
                    })
                    .catch(() => {});
            },




            // 下线操作
            handleOffline(index, row) {
                // 二次确认下线
                this.$confirm('确定要下线吗？', '提示', {
                    type: 'warning'
                })
                    .then(() => {
                        this.$message.success('下线成功');
                        this.tableData.splice(index, 1);
                    })
                    .catch(() => {});
            },


            changeDate(){
                const now = new Date().getTime();
                this.data.forEach((item, index) => {
                    const date = new Date(now - (6 - index) * 86400000);
                    item.name = `${date.getFullYear()}/${date.getMonth()+1}/${date.getDate()}`
                })
            },
            handleListener(){
                bus.$on('collapse', this.handleBus);
                // 调用renderChart方法对图表进行重新渲染
                window.addEventListener('resize', this.renderChart)
            },
            handleBus(msg){
                setTimeout(() => {
                    this.renderChart()
                }, 300);
            },
            renderChart(){
                this.$refs.bar.renderChart();
                this.$refs.line.renderChart();
            }
        }
    }

</script>


<style scoped>
    .el-row {
        margin-bottom: 20px;
    }

    .grid-content {
        display: flex;
        align-items: center;
        height: 100px;
    }

    .grid-cont-right {
        flex: 1;
        text-align: center;
        font-size: 14px;
        color: #999;
    }

    .grid-num {
        font-size: 30px;
        font-weight: bold;
    }

    .grid-con-icon {
        font-size: 50px;
        width: 100px;
        height: 100px;
        text-align: center;
        line-height: 100px;
        color: #fff;
    }

    .grid-con-1 .grid-con-icon {
        background: rgb(45, 140, 240);
    }

    .grid-con-1 .grid-num {
        color: rgb(45, 140, 240);
    }

    .grid-con-2 .grid-con-icon {
        background: rgb(100, 213, 114);
    }

    .grid-con-2 .grid-num {
        color: rgb(45, 140, 240);
    }

    .grid-con-3 .grid-con-icon {
        background: rgb(242, 94, 67);
    }

    .grid-con-3 .grid-num {
        color: rgb(242, 94, 67);
    }

    .user-info {
        display: flex;
        align-items: center;
        padding-bottom: 20px;
        border-bottom: 2px solid #ccc;
        margin-bottom: 20px;
    }

    .user-avator {
        width: 120px;
        height: 120px;
        border-radius: 50%;
    }

    .user-info-cont {
        padding-left: 50px;
        flex: 1;
        font-size: 14px;
        color: #999;
    }

    .user-info-cont div:first-child {
        font-size: 30px;
        color: #222;
    }

    .user-info-list {
        font-size: 14px;
        color: #999;
        line-height: 25px;
    }

    .user-info-list span {
        margin-left: 70px;
    }

    .mgb20 {
        margin-bottom: 20px;
    }

    .todo-item {
        font-size: 14px;
    }

    .todo-item-del {
        text-decoration: line-through;
        color: #999;
    }

    .schart {
        width: 100%;
        height: 300px;
    }

</style>
