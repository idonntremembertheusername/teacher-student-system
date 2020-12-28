<template>
    <div>
        <div class="crumbs" style="margin-left: 10%;margin-right: 10%" >
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 资料管理
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container" style="margin-left: 10%;margin-right: 10%">
            <el-button type="primary" @click="back" v-if="backStatus == 1">返回上一层</el-button>&nbsp;&nbsp
            <el-button type="primary" @click="showPublishMaterial" v-if="this.$cookies.get('role') == 2">资料上传</el-button>&nbsp;
            文档类型：
            <el-select v-model="query.status" placeholder="请选择" style="padding: 10px">
                <el-option  label="全部" value="" ></el-option>
                <el-option  label="课件" value="1" ></el-option>
                <el-option  label="习题" value="2"></el-option>
                <el-option  label="答案" value="3"></el-option>
            </el-select>
            &nbsp;
            <el-input  placeholder="课程" class="handle-input mr10" v-model="query.courseName"></el-input>
            &nbsp;
            <el-input  placeholder="教师名称" class="handle-input mr10" v-model="query.teacherName" v-if="role!=2"></el-input>
            <el-button type="primary" icon="el-icon-search" @click="handleSearch" >搜索</el-button>

            <el-table
                :data="materials"
                border
                class="table"
                ref="multipleTable"
                header-cell-class-name="table-header"
            >

                <el-table-column prop="name" label="文档名"></el-table-column>
                <el-table-column prop="status" label="文档类型">
                    <template slot-scope="scope">
                        <span v-if="scope.row.status == '1'"> 课件</span>
                        <span v-else-if="scope.row.status == '2'"> 习题</span>
                        <span v-else-if="scope.row.status == '3'"> 答案</span>
                    </template>
                </el-table-column>
                <el-table-column prop="courseName" label="课程"></el-table-column>
                <el-table-column prop="teacher" label="上传者"></el-table-column>



                <el-table-column prop="createTime" label="上传时间"></el-table-column>
                <el-table-column label="操作" width="180" align="center">
                    <template slot-scope="scope">
                        <el-button @click="getFile(scope.row.name,scope.row.teacherId)">查看</el-button>
                        <el-button type="danger" v-if="scope.row.teacherId == userId" @click="deleteFile(scope.row.name,scope.row.materialId)">删除</el-button>
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

        <!-- 上传资料-->
        <el-dialog title="上传作业" :visible.sync="publishMaterialVisible" width="40%">
            <el-form ref="form" :model="form" label-width="70px">
                <el-form-item label="课程名称">
                    <el-col :span="6">
                        <el-select v-model="material.courseId" placeholder="请选择" style="padding: 10px" @change="changeCourseValue">
                            <el-option v-for="course in courses" :label="course.name" :value="course.courseId" ></el-option>
                        </el-select>
                    </el-col>
                </el-form-item>

                <el-form-item label="资料类型">
                    <el-col :span="6">
                        <el-select v-model="material.status" placeholder="请选择" style="padding: 10px">
                            <el-option  label="课件" value="1" ></el-option>
                            <el-option  label="习题" value="2"></el-option>
                            <el-option  label="答案" value="3"></el-option>
                        </el-select>
                    </el-col>
                </el-form-item>
                <el-form-item label="文件" >
                    <input type="file" ref="myfile" >
                </el-form-item>
            </el-form>

            <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="addHomeWork">确 定</el-button>
                <el-button @click="publishMaterialVisible = false">取 消</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
export default {
    data() {
        return {
            backStatus: 0,
            role:"",
            userId: "",
            courses:"",
            material:{
                status:"",
                teacher:"",
                teacherId:"",
                courseId:"",
                type:"",
                name:"",
            },
            publishMaterialVisible:false,
            page:{
                pages:1,
                total:1,
            },
            materials:[],
            doctype:'',
            fileList: [{name: 'food.doc', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}],
            uploadHistry:[{
                id:'2',
                class:'面向对象开发',
                name:'第一章课件',
                type:'课件',
                person:'李教授',
                teacher:'李教授',
                date:'2019-10-26',
            },
            ],
            query: {
                status: "",
                role: "",
                teacherName:"",
                courseName: "",
                userId:"",
                size: 10,
                current: 1,
            },
            tableData: [],
            multipleSelection: [],
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
    watch: {
        'query.status': { // 监视pagination属性的变化
            // deep: true, // deep为true，会监视pagination的属性及属性中的对象属性变化
            handler(val) {
                this.initPage();
                // 变化后的回调函数，这里我们再次调用getDataFromServer即可
                this.loadData();
            }
        },


    },
    methods: {
        //返回上一层
        back(){
            this.$router.go(-1)
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
        deleteFile(name,materialId){
            // 二次确认删除资料
            this.$confirm('确定要删除资料吗？', '提示', {
                type: 'warning'
            })
                .then(() => {
                    this.$deleteRequest("Material?materialId="+materialId).then(res=>{
                        this.$deleteRequest("/File?userId="+this.userId+"&name="+name).then(res=>{})
                        this.$message.success(res.message)
                        this.loadData()
                    })


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
            formData.append("name", this.material.name);
            formData.append("userId", this.$cookies.get("userId"));
            this.$upload("/File",formData).then(resp=>{
                this.$refs.myfile = null

                //数据库操作
                this.material.name = resp.message
                this.material.teacher = this.$cookies.get("name")
                this.material.teacherId = this.$cookies.get("userId")
                this.$post("/Material",this.material).then(res=>{
                    this.$message.success(res.message);
                    this.publishMaterialVisible = false;
                    this.material.teacher = "",
                    this.material.teacherId = "",
                    this.material.name = "",
                    this.material.userId = "",
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
            this.material.courseName = obj.name;
        },
        //发布资料弹窗
        showPublishMaterial(){
            //获取教师的course信息
            this.$fetch("/Course/teacher/"+this.$cookies.get("userId")).then(res=>{
                this.courses = res.data;
            })
            this.publishMaterialVisible = true
        },
        //加载数据
        loadData(){

            this.$fetch("/Material",this.query).then(res=>{
                this.materials = res.data.records;
                this.page.total = res.data.total;
                this.page.pages = res.data.pages;
                this.query.size = res.data.size;

            })

        },
        // 搜索资料
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






        // 删除操作
        handleDelete(index, row) {
            // 二次确认删除
            this.$confirm('确定要删除吗？', '提示', {
                type: 'warning'
            })
                .then(() => {
                    this.$message.success('删除成功');
                    this.tableData.splice(index, 1);
                })
                .catch(() => {});
        },
        // 多选操作
        handleSelectionChange(val) {
            this.multipleSelection = val;
        },
        delAllSelection() {
            const length = this.multipleSelection.length;
            let str = '';
            this.delList = this.delList.concat(this.multipleSelection);
            for (let i = 0; i < length; i++) {
                str += this.multipleSelection[i].name + ' ';
            }
            this.$message.error(`删除了${str}`);
            this.multipleSelection = [];
        },
        // 编辑操作
        handleEdit(index, row) {
            this.idx = index;
            this.form = row;
            this.editVisible = true;
        },
        // 保存编辑
        saveEdit() {
            this.editVisible = false;
            this.$message.success(`修改第 ${this.idx + 1} 行成功`);
            this.$set(this.tableData, this.idx, this.form);
        },
        // 分页导航
        handlePageChange(val) {
            this.$set(this.query, 'pageIndex', val);
            this.getData();
        }
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
