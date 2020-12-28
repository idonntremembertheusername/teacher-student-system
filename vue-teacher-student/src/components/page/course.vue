<template>
    <div>
        <div class="crumbs"  >
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 课程管理
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container"  >
            <div class="handle-box">
                <el-button type="primary" v-if="role == 2" @click="showPublish">发布课程</el-button>
                &emsp;
                <el-button type="primary" @click="joinCourse"  v-if="role == 3">加入课程</el-button>&emsp;&emsp;
                &emsp;
                <el-input v-model="query.teacherName" placeholder="教师名" class="handle-input mr10"></el-input>
                &emsp;
                <el-input v-model="query.courseName" placeholder="课程名" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
            </div>
            <el-table
                :data="courses"
                border
                class="table"
                ref="multipleTable"
                header-cell-class-name="table-header"
                @selection-change="handleSelectionChange"
            >

                <el-table-column prop="name"  label="名称"></el-table-column>
                <el-table-column prop="code" label="邀请码">
                    <template slot-scope="scope">{{scope.row.code}}</template>
                </el-table-column>
                <el-table-column prop="teacherName" label="教师">
                    <template slot-scope="scope">{{scope.row.teacherName}}</template>
                </el-table-column>
                <el-table-column prop="address" label="已上传资料">
                    <template slot-scope="scope">{{scope.row.mnumber}}</template>
                </el-table-column>
                <el-table-column prop="address" label="已加入学生数">
                    <template slot-scope="scope">{{scope.row.snumber}}</template>
                </el-table-column>
                <el-table-column prop="address" label="已提交作业数">
                    <template slot-scope="scope">{{scope.row.hnumber}}</template>
                </el-table-column>



                <el-table-column prop="date" label="创建时间" width="160">
                    <template slot-scope="scope">{{scope.row.createTime}}</template>
                </el-table-column>
                <el-table-column label="操作" width="380" align="center">
                    <template slot-scope="scope">
                        <el-button  @click="showDetail(scope.row.content)">详情</el-button>
                        <el-button  @click="showMaterial(scope.row.name)">资料</el-button>
                        <el-button  @click="showStudent(scope.row.courseId)" v-if="role!=3">学生</el-button>
                        <el-button  @click="showHomework(scope.row.name)">作业</el-button>
                        <el-button type="danger" @click="handleForbid(scope.row.courseId)" v-if="scope.row.status == 1 &&role!=3">停用</el-button>
                        <el-button type="success" @click="handleUse(scope.row.courseId)" v-if="scope.row.status == 0&&role!=3">启用</el-button>
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




<!--        &lt;!&ndash; 查看学生弹出框 &ndash;&gt;-->
<!--        <el-dialog title="查看学生" :visible.sync="studentVisible" width="70%">-->
<!--            <div class="handle-box">-->


<!--                <el-input v-model="query.name" placeholder="用户名" class="handle-input mr10"></el-input>-->
<!--                <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>-->
<!--            </div>-->
<!--            <el-table-->
<!--                    :data="companyData2"-->
<!--                    border-->
<!--                    class="table"-->
<!--                    ref="multipleTable"-->
<!--                    header-cell-class-name="table-header"-->
<!--                    @selection-change="handleSelectionChange"-->
<!--            >-->

<!--                <el-table-column prop="name"  label="账号"></el-table-column>-->
<!--                <el-table-column prop="address" label="提问次数">-->
<!--                    <template slot-scope="scope">{{scope.row.ask}}</template>-->
<!--                </el-table-column>-->
<!--                <el-table-column prop="address" label="回答次数">-->
<!--                    <template slot-scope="scope">{{scope.row.answer}}</template>-->
<!--                </el-table-column>-->



<!--                <el-table-column prop="date" label="注册时间">-->
<!--                    <template slot-scope="scope">{{scope.row.registTime}}</template>-->
<!--                </el-table-column>-->
<!--            </el-table>-->
<!--            <div class="pagination">-->
<!--                <el-pagination-->
<!--                        background-->
<!--                        layout="total, prev, pager, next"-->
<!--                        :current-page="query.pageIndex"-->
<!--                        :page-size="query.pageSize"-->
<!--                        :total="pageTotal"-->
<!--                        @current-change="handlePageChange"-->
<!--                ></el-pagination>-->
<!--            </div>-->
<!--        </el-dialog>-->

<!--        &lt;!&ndash; 查看资料弹出框 &ndash;&gt;-->
<!--        <el-dialog title="查看资料" :visible.sync="materialVisible" width="70%">-->
<!--            文档类型：-->
<!--            <el-select v-model="doctype" placeholder="请选择" style="padding: 10px">-->
<!--                <el-option key="bbk" label="课件" value="bbk" ></el-option>-->
<!--                <el-option key="xtc" label="作业" value="xtc"></el-option>-->
<!--                <el-option key="s" label="习题" value="2"></el-option>-->

<!--            </el-select>-->
<!--            &nbsp;-->
<!--            <el-table-->
<!--                    :data="uploadHistry"-->
<!--                    border-->
<!--                    class="table"-->
<!--                    ref="multipleTable"-->
<!--                    header-cell-class-name="table-header"-->
<!--            >-->

<!--                <el-table-column prop="name" label="文档名"></el-table-column>-->
<!--                <el-table-column prop="type" label="文档类型"></el-table-column>-->
<!--                <el-table-column prop="class" label="课程"></el-table-column>-->
<!--                <el-table-column prop="person" label="上传者"></el-table-column>-->



<!--                <el-table-column prop="date" label="上传时间"></el-table-column>-->
<!--                <el-table-column label="操作" width="180" align="center">-->
<!--                    <template slot-scope="scope">-->
<!--                        <el-button-->
<!--                                type="text"-->
<!--                                icon="el-icon-document"-->
<!--                        >查看</el-button>-->
<!--                    </template>-->
<!--                </el-table-column>-->
<!--            </el-table>-->
<!--            <div class="pagination">-->
<!--                <el-pagination-->
<!--                        background-->
<!--                        layout="total, prev, pager, next"-->
<!--                        :current-page="query.pageIndex"-->
<!--                        :page-size="query.pageSize"-->
<!--                        :total="pageTotal"-->
<!--                        @current-change="handlePageChange"-->
<!--                ></el-pagination>-->
<!--            </div>-->
<!--        </el-dialog>-->



<!--        &lt;!&ndash; 查看作业弹出框 &ndash;&gt;-->
<!--        <el-dialog title="查看作业" :visible.sync="homeworkVisible" width="70%">-->
<!--            &nbsp;-->
<!--            <el-input  placeholder="学生名称" class="handle-input mr10"></el-input>-->
<!--            <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>-->
<!--            <div style="height: 1rem"></div>-->
<!--            <el-table-->
<!--                    :data="uploadHistry2"-->
<!--                    border-->
<!--                    class="table"-->
<!--                    ref="multipleTable"-->
<!--                    header-cell-class-name="table-header"-->
<!--            >-->

<!--                <el-table-column prop="name" label="文档名"></el-table-column>-->
<!--                <el-table-column prop="class" label="课程"></el-table-column>-->
<!--                <el-table-column prop="person" label="上传者"></el-table-column>-->
<!--                <el-table-column prop="goal" label="分数"></el-table-column>-->




<!--                <el-table-column prop="date" label="上传时间"></el-table-column>-->
<!--                <el-table-column label="操作" width="180" align="center">-->
<!--                    <template slot-scope="scope">-->
<!--                        <el-button>查看</el-button>-->
<!--                        <el-button type="success" @click="showScore">评分</el-button>-->
<!--                    </template>-->
<!--                </el-table-column>-->
<!--            </el-table>-->
<!--            <div class="pagination">-->
<!--                <el-pagination-->
<!--                        background-->
<!--                        layout="total, prev, pager, next"-->
<!--                        :current-page="query.pageIndex"-->
<!--                        :page-size="query.pageSize"-->
<!--                        :total="pageTotal"-->
<!--                        @current-change="handlePageChange"-->
<!--                ></el-pagination>-->
<!--            </div>-->
<!--        </el-dialog>-->

<!--        &lt;!&ndash; 评分&ndash;&gt;-->
<!--        <el-dialog title="评分" :visible.sync="scoreVisible" width="30%">-->
<!--            <div class="handle-box">-->
<!--                请输入分数：<el-input v-model="query.name" placeholder="分数" class="handle-input mr10"></el-input>-->
<!--            </div>-->
<!--            <span slot="footer" class="dialog-footer">-->
<!--                <el-button type="primary" >确 定</el-button>-->
<!--                <el-button @click="scoreVisible = false">取 消</el-button>-->
<!--            </span>-->
<!--        </el-dialog>-->



        <!-- 发布课程-->
        <el-dialog title="发布课程" :visible.sync="publishVisible" width="70%">
            <el-form ref="form" :model="course" label-width="70px">
                <el-form-item label="课程名称">
                    <el-col :span="6">
                        <el-input v-model="course.name"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="课程详情" >
                    <quill-editor ref="myTextEditor" v-model="course.content" ></quill-editor>
                </el-form-item>
            </el-form>

            <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="addCourse">确 定</el-button>
                <el-button @click="publishVisible = false">取 消</el-button>
            </span>
        </el-dialog>

        <!-- 课程详情弹出框 -->
        <el-dialog title="课程详情" :visible.sync="detailVisible" width="60%">
            <div v-html="content" style=" font-size: 18px;line-height: 25px;padding: 30px"></div>
        </el-dialog>

        <!-- 加入课程-->
        <el-dialog title="加入课程" :visible.sync="joinCourseVisible" width="40%">
            <el-form ref="form" :model="code" label-width="70px">
                <el-form-item label="邀请码">
                    <el-col :span="6">
                        <el-input v-model="code"></el-input>
                    </el-col>
                </el-form-item>
            </el-form>

            <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="join">确 定</el-button>
                <el-button @click="joinCourseVisible = false">取 消</el-button>
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
    data() {
        return {
            code:"",
            joinCourseVisible: false,
            content:"",
            course:{
                name:"",
                content:"",
                teacherId:"",
                teacherName:"",
            },
            publishVisible: false,
            role:"",
            userId: "",
            courses:[],
            query: {
                role:"",
                userId:"",
                courseName: '',
                teacherName: '',
                size: 10,
                current: 1,
            },

            page:{
                pages:1,
                total:1,
            },






            uploadHistry2:[{
                id:'2',
                name:'第一次作业',
                person:'吴一帆',
                goal: 80,
                class:'面向对象开发',
                teacher:'李教授',
                date:'2019-10-26',
            },
            ],
            companyData2: [
                {
                    id: 1,
                    name: "91112322@qq.com",
                    registTime: '2020 1 10',
                    stage: "正常使用",
                    role:'管理员',
                    ask: 1,
                    answer: 2,
                    status: 0
                },
                {
                    id: 2,
                    name: "43322322@qq.com",
                    registTime: '2020 1 10',
                    stage: "已注销",
                    ask: 1,
                    answer: 2,
                    role:'管理员',
                    status: 1
                },
                {
                    id: 3,
                    name: "652322@qq.com",
                    registTime: '2020 1 10',
                    stage: "已注销",
                    ask: 1,
                    answer: 2,
                    role:'教师',
                    status: 1
                },
                {
                    id: 4,
                    name: "6633232@qq.com",
                    registTime: '2020 1 10',
                    stage: "正常使用",
                    ask: 1,
                    answer: 2,
                    role:'学生',
                    status: 0
                },

            ],
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
            homeworkVisible: false,
            detailVisible: false,
            studentVisible: false,
            materialVisible: false,
            scoreVisible: false,
            options1: [{
                value: '1',
                label: '全部'
            }, {
                value: '2',
                label: '已注销'
            }, {
                value: '3',
                label: '正常使用'
            },
            ],
            options: [{
                value: '1',
                label: '管理员'
            }, {
                value: '2',
                label: '教师'
            }, {
                value: '3',
                label: '学生'
            },
            ],
            value: '',
            value1: '',
            fits: ['fill', 'contain', 'cover', 'none', 'scale-down'],
            url: 'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg',
            examineVisible: false,
            status: 1,

            companyData: [
                {
                    id: 1,
                    name: "java",
                    registTime: '2020 1 10',
                    teacher: 'a',
                    student: 12,
                    material: 2,
                    status: 0,
                    code: 'ddxscdcccss',
                },
                {
                    id: 2,
                    name: "c",
                    registTime: '2020 1 10',
                    teacher: 'e',
                    student: 12,
                    material: 2,
                    status: 1,
                    code: 'ddxscdcccss',
                },
                {
                    id: 3,
                    name: "php",
                    registTime: '2020 1 10',
                    teacher: 'w',
                    student: 12,
                    material: 2,
                    status: 1,
                    code: 'ddxscdcccss',
                },
                {
                    id: 4,
                    name: "c++",
                    registTime: '2020 1 10',
                    teacher: 'q',
                    student: 12,
                    material: 2,
                    status: 0,
                    code: 'ddxscdcccss',
                },

            ],
            multipleSelection: [],
            delList: [],
            editVisible: false,
            pageTotal: 0,
            form: {},
            idx: -1,
            id: -1
        };
    },
    created() {
        this.role = this.$cookies.get("role")
        this.userId =this.$cookies.get('userId')
        this.query.role = this.$cookies.get("role")
        this.query.userId = this.$cookies.get("userId")
        this.loadData();
    },
    components: {
        quillEditor
    },
    methods: {
        //查看课程资料
        showMaterial(courseName){
            this.$router.push("/material-manager?courseName="+courseName+"&back="+1)
        },
        //查看课程作业
        showHomework(courseName){
            this.$router.push("/homework-manager?courseName="+courseName+"&back="+1)
        },
        //查看课程学生
        showStudent(courseId){
            this.$router.push("/student-teacher?courseId="+courseId+"&back="+1)
        },


        //加入课程弹窗
        join(){
            this.$patch("/Course/join?userId="+this.userId+"&code="+this.code).then(res=>{
                this.$message.success(res.message);
                this.code = ""
                this.loadData()
                this.joinCourseVisible = false
            })
        },
        //加入课程弹窗
        joinCourse(){
            this.joinCourseVisible = true
        },
        // 启用操作
        handleUse(courseId) {
            // 二次确认启用
            this.$confirm('确定要启用吗？', '提示', {
                type: 'warning'
            })
                .then(() => {
                    this.$patch("/Course?courseId="+courseId).then(res=>{
                        this.$message.success(res.message);
                        this.loadData()
                    })
                })
                .catch(() => {});
        },

        // 禁用操作
        handleForbid(courseId) {
            // 二次确认启用
            this.$confirm('确定要禁用吗？', '提示', {
                type: 'warning'
            })
                .then(() => {
                    this.$patch("/Course?courseId="+courseId).then(res=>{
                        this.$message.success(res.message);
                        this.loadData()
                    })
                })
                .catch(() => {});
        },



        //查看课程详情
        showDetail(content){
            this.content = content
            this.detailVisible = true
        },
        // 搜索课程
        handleSearch() {
            this.loadData();
        },
        //发布课程操作
        addCourse(){
            this.course.teacherId = this.$cookies.get("userId")
            this.course.teacherName = this.$cookies.get("name")
            this.$post("/Course",this.course).then(res=>{
                this.$message.success(res.message)
                this.loadData()
                this.publishVisible = false
            })
        },
        //发布课程弹窗
        showPublish(){
            this.publishVisible = true
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
        loadData(){
            this.$fetch("/Course",this.query).then(res=>{
                this.courses = res.data.records;
                this.query.current = res.data.current;
                this.query.size = res.data.size;
                this.page.pages = res.data.pages;
                this.page.total = res.data.total;

            })
        },





        showScore(){
            this.scoreVisible = true
        },


        ShowExamine(){
            this.examineVisible = true
        },









        // 冻结操作
        handleFreeze(index, row) {
            // 二次确认冻结
            this.$confirm('确定要冻结吗？', '提示', {
                type: 'warning'
            })
                .then(() => {
                    this.$message.success('冻结用户成功');
                    this.tableData.splice(index, 1);
                })
                .catch(() => {});
        },
        // 重置密码操作
        handleReset(index, row) {
            // 二次确认重置密码
            this.$confirm('确定要重置密码吗？', '提示', {
                type: 'warning'
            })
                .then(() => {
                    this.$message.success('重置密码成功');
                    this.tableData.splice(index, 1);
                })
                .catch(() => {});
        },


        // 解冻操作
        handleUnFreeze(index, row) {
            // 二次确认解冻
            this.$confirm('确定要解冻吗？', '提示', {
                type: 'warning'
            })
                .then(() => {
                    this.$message.success('解冻用户成功');
                    this.tableData.splice(index, 1);
                })
                .catch(() => {});
        },

        // 注销操作
        handleDelete(index, row) {
            // 二次确认注销
            this.$confirm('确定要注销吗？', '提示', {
                type: 'warning'
            })
                .then(() => {
                    this.$message.success('注销用户成功');
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
            this.examineVisible = false;
            this.$message.success(`修改第 ${this.idx + 1} 行成功`);
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
