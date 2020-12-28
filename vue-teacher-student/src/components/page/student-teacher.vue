<template>
    <div>
        <div class="crumbs" style="margin-left: 10%;margin-right: 10%" >
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 学生管理
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container" style="margin-left: 10%;margin-right: 10%" >
            <div class="handle-box">
                <el-button type="primary" @click="back" v-if="backStatus == 1">返回上一层</el-button>
                &emsp;&emsp;
                <el-input v-model="query.studentName" placeholder="用户名" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
            </div>
            <el-table
                :data="users"
                border
                class="table"
                ref="multipleTable"
                header-cell-class-name="table-header"
                @selection-change="handleSelectionChange"
            >

                <el-table-column prop="name"  label="账号"></el-table-column>
                <el-table-column prop="address" label="课程">
                    <template slot-scope="scope">
                        <span v-if="scope.row.coursename == null">java</span>
                        {{scope.row.coursename}}
                    </template>
                </el-table-column>
                <el-table-column prop="address" label="提问次数">
                    <template slot-scope="scope">{{scope.row.fnumber}}</template>
                </el-table-column>
                <el-table-column prop="address" label="回答次数">
                    <template slot-scope="scope">{{scope.row.mnumber}}</template>
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


    </div>
</template>

<script>
import { fetchData } from '../../api/index';
export default {
    name: 'basetable',
    data() {
        return {
            backStatus:0,
            users:"",
            query: {
                studentName: '',
                courseId: '',
                userId:"",
                size: 10,
                current: 1,
            },
            page:{
                pages:1,
                total:1,
            },





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
                    name: "244355",
                    registTime: '2020 1 10',
                    stage: "正常使用",
                    role:'管理员',
                    ask: 1,
                    answer: 2,
                    status: 0,
                    course: 'java',
                },
                {
                    id: 2,
                    name: "76654556",
                    registTime: '2020 1 10',
                    stage: "已注销",
                    ask: 1,
                    answer: 2,
                    role:'管理员',
                    status: 1,
                    course: 'java',
                },
                {
                    id: 3,
                    name: "122323",
                    registTime: '2020 1 10',
                    stage: "已注销",
                    ask: 1,
                    answer: 2,
                    role:'教师',
                    status: 1,
                    course: 'java',
                },
                {
                    id: 4,
                    name: "44343242",
                    registTime: '2020 1 10',
                    stage: "正常使用",
                    ask: 1,
                    answer: 2,
                    role:'学生',
                    status: 0,
                    course: 'java',
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
        this.backStatus = this.$route.query.back
        this.query.courseId = this.$route.query.courseId
        this.query.userId = this.$cookies.get("userId")
        this.loadData();
    },

    methods: {
        //返回上一层
        back(){
            this.$router.go(-1)
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
            this.$fetch("/User/student",this.query).then(res=>{
                this.users = res.data.records;
                this.query.current = res.data.current;
                this.query.size = res.data.size;
                this.page.pages = res.data.pages;
                this.page.total = res.data.total;

            })
        },


        ShowExamine(){
            this.examineVisible = true
        },

        // 获取 easy-mock 的模拟数据
        getData() {
            fetchData(this.query).then(res => {
                this.tableData = res.list;
                this.pageTotal = res.pageTotal || 50;
            });
        },
        // 搜索学生
        handleSearch() {
            this.loadData();
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
