<template>
    <div>
        <div class="crumbs" style="margin-left: 10%;margin-right: 10%" >
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 作业管理
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container" style="margin-left: 10%;margin-right: 10%" >

            <el-button type="primary" @click="back" v-if="backStatus == 1">返回上一层</el-button>
            &nbsp;<el-button type="primary" @click="showPublishHomework" v-if="this.$cookies.get('role') == 3">作业上传</el-button>&nbsp;
            <el-input  placeholder="课程" class="handle-input mr10" v-model="query.courseName"></el-input>
            &nbsp;
            <el-input  placeholder="学生名称" class="handle-input mr10" v-model="query.studentName"></el-input>
            <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
            <div style="height: 1rem"></div>
            <el-table
                :data="homeworks"
                border
                class="table"
                ref="multipleTable"
                header-cell-class-name="table-header"
            >

                <el-table-column prop="name" label="文档名"></el-table-column>
                <el-table-column prop="courseName" label="课程"></el-table-column>
                <el-table-column prop="author" label="上传者"></el-table-column>
                <el-table-column prop="score" label="分数">
                    <template slot-scope="scope">
                        <span v-if="scope.row.score == ''||scope.row.score == null">
                            未评分
                        </span>
                        <span v-else-if="scope.row.score != ''&&scope.row.score != null">
                            {{scope.row.score}}
                        </span>
                    </template>
                </el-table-column>




                <el-table-column prop="createTime" label="上传时间"></el-table-column>
                <el-table-column label="操作" width="210" align="center">
                    <template slot-scope="scope">
                        <el-button @click="getFile(scope.row.name,scope.row.userId)">查看</el-button>
                        <el-button type="success" v-if="role == 2&&(scope.row.score == ''||scope.row.score == null)" @click="showScore(scope.row.homeworkId)">评分</el-button>
                        <el-button type="danger" v-if="scope.row.userId == userId" @click="deleteFile(scope.row.name,scope.row.homeworkId)">删除</el-button>
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
        </div>

        <!-- 上传作业-->
        <el-dialog title="上传作业" :visible.sync="publishHomeworkVisible" width="40%">
            <el-form ref="form" :model="form" label-width="70px">

                <el-form-item label="课程名称">
                    <el-col :span="6">
                        <el-select v-model="homework.courseId" placeholder="请选择" style="padding: 10px" @change="changeCourseValue">
                            <el-option v-for="course in courses" :label="course.name" :value="course.courseId" ></el-option>
                        </el-select>
                    </el-col>
                </el-form-item>
                <el-form-item label="文件" >
                    <input type="file" ref="myfile" >
                </el-form-item>
            </el-form>

            <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="addHomeWork">确 定</el-button>
                <el-button @click="publishHomeworkVisible = false">取 消</el-button>
            </span>
        </el-dialog>

        <!-- 评分-->
        <el-dialog title="评分" :visible.sync="scoreVisible" width="30%">
            <div class="handle-box">
                请输入分数：<el-input v-model="score" placeholder="分数" class="handle-input mr10"></el-input>
            </div>
            <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="setScore">确 定</el-button>
                <el-button @click="scoreVisible = false">取 消</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
export default {
    data() {
        return {
            backStatus: 0,
            homeworkId: "",
            score:"",
            role:"",
            userId: "",
            scoreVisible: false,
            form:{
                file:"",
                userId:"",
                name:"",
            },
            courses:[],
            homework:{
                courseId: "",
                courseName: "",
                name: "",
                author:"",
                userId:"",
            },
            publishHomeworkVisible: false,
            homeworks:[],
            query: {
                role:"",
                userId:"",
                courseName: '',
                studentName: '',
                size: 10,
                current: 1,
            },
            page:{
                pages:1,
                total:1,
            },





            doctype:'',
            fileList: [{name: 'food.doc', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}],






            delList: [],
            uploadVisible: false,
            pageTotal: 0,
            form: {},
            idx: -1,
            id: -1
        };
    },
    created() {
        this.backStatus = this.$route.query.back
        this.query.courseName = this.$route.query.courseName
        this.role = this.$cookies.get("role")
        this.userId =this.$cookies.get('userId')
        this.query.role = this.$cookies.get("role")
        this.query.userId = this.$cookies.get("userId")
        this.loadData();
    },
    mounted(){
        this.loadData();
    },
    methods: {
        //返回上一层
        back(){
            this.$router.go(-1)
        },
        //评分
        setScore(){
            this.$patch("/Homework?homeworkId="+this.homeworkId+"&score="+this.score).then(res=>{
                this.$message.success(res.message)
                this.score= ""
                this.courseId = ""
                this.loadData()
                this.scoreVisible = false

            })
        },
        //评分弹窗
        showScore(homeworkId){
            this.homeworkId = homeworkId
            this.scoreVisible = true
        },


        //获取文件
        getFile(name,userId){
            this.$getFile("/File?name="+name+"&userId="+userId).then(res=>{
                let url = window.URL.createObjectURL(res.data); //表示一个指定的file对象或Blob对象
                let a = document.createElement("a");
                document.body.appendChild(a);
                // let filename= res.headers["content-disposition"].split(";")[1].split("filename=")[1];  //filename名称截取
                a.href = url;
                a.download = name; //命名下载名称
                a.click(); //点击触发下载
                window.URL.revokeObjectURL(url);  //下载完成进行释放
            })
        },
        //删除文件
        deleteFile(name,homeworkId){
            // 二次确认删除作业
            this.$confirm('确定要删除作业吗？', '提示', {
                type: 'warning'
            })
                .then(() => {
                    this.$deleteRequest("Homework?homeworkId="+homeworkId).then(res=>{
                        this.$deleteRequest("/File?userId="+this.userId+"&name="+name).then(res=>{})
                        this.$message.success(res.message)
                    })

                    this.loadData()
                })
                .catch(() => {});

        },


        //上传作业
        addHomeWork(){
            //文件上传
            let myFile = this.$refs.myfile;
            let files = myFile.files;
            let file = files[0];
            let formData = new FormData();
            formData.append("file", file);
            formData.append("name", this.homework.name);
            formData.append("userId", this.$cookies.get("userId"));
            this.$upload("/File",formData).then(resp=>{
                this.$refs.myfile = null

                //数据库操作
                this.homework.name = resp.message
                this.homework.author = this.$cookies.get("name")
                this.homework.userId = this.$cookies.get("userId")
                this.$post("/Homework",this.homework).then(res=>{
                    this.$message.success(res.message);
                    this.publishHomeworkVisible = false;
                    this.homework.author = "",
                        this.homework.author = "",
                        this.homework.name = "",
                        this.homework.userId = "",
                        this.homework.author = "",
                        this.loadData()
                })
            })
        },
        //获取lable值
        changeCourseValue(val){
                let obj = {};
                obj = this.courses.find((course)=>{
                    return course.courseId === val;
                });
                this.homework.courseName = obj.name;
        },
        //作业上传弹窗
        showPublishHomework(){
            //获取学生的course信息
            this.$fetch("/Course/"+this.$cookies.get("userId")).then(res=>{
                this.courses = res.data;
            })
            this.publishHomeworkVisible = true
        },
        // 查询作业
        handleSearch() {
            this.loadData();
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
        loadData(){
            this.$fetch("/Homework",this.query).then(res=>{
                this.homeworks = res.data.records;
                this.page.total = res.data.total;
                this.page.pages = res.data.pages;
                this.query.size = res.data.size;

            })
        },
    }
};
</script>

<style scoped>
.handle-box {
    margin-bottom: 20px;
}

.handle-select {
    width: 120px;
}

.handle-input {
    width: 300px;
    display: inline-block;
}
.table {
    width: 100%;
    font-size: 14px;
}
.red {
    color: #ff0000;
}
.mr10 {
    margin-right: 10px;
}
.table-td-thumb {
    display: block;
    margin: auto;
    width: 40px;
    height: 40px;
}
</style>
