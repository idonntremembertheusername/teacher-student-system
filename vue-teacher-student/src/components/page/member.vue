<template>
    <div>
        <div class="crumbs" style="margin-left: 10%;margin-right: 10%" >
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 用户管理
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container" style="margin-left: 10%;margin-right: 10%" align="center">
            <div class="handle-box">

                状态：<el-select v-model="query.status" placeholder="请选择">
                <el-option
                        v-for="item in options1"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                </el-option>
            </el-select>
                &emsp;
                角色：<el-select v-model="query.role" placeholder="请选择">
                    <el-option
                            v-for="item in options"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value">
                    </el-option>
                </el-select>
                &emsp;
                <el-input v-model="query.name" placeholder="用户名" class="handle-input mr10"></el-input>
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

                <el-table-column prop="account"  label="账号"></el-table-column>
                <el-table-column prop="role" label="角色">
                    <template slot-scope="scope">
                        <span v-if="scope.row.role == '1'"> 管理员</span>
                        <span v-else-if="scope.row.role == '2'"> 教师</span>
                        <span v-else-if="scope.row.role == '3'"> 学生</span>
                    </template>
                </el-table-column>
                <el-table-column prop="status" label="状态">
                    <template slot-scope="scope">
                        <span v-if="scope.row.status == '1'"> 正常使用</span>
                        <span v-else-if="scope.row.status == '2'"> 已注销</span>
                    </template>
                </el-table-column>
                <el-table-column prop="fnumber" label="提问次数">
                    <template slot-scope="scope">{{scope.row.fnumber}}</template>
                </el-table-column>
                <el-table-column prop="mnumber" label="回答次数">
                    <template slot-scope="scope">{{scope.row.mnumber}}</template>
                </el-table-column>


                <el-table-column label="操作" width="250" align="center">
                    <template slot-scope="scope">
                        <el-button  @click="handleReset(scope.row.userId)">重置密码</el-button>
                        <el-button type="danger" @click="handleDelete(scope.row.userId)" v-if="scope.row.status == 1">注销</el-button>
                        <el-button type="success" @click="handleReUse(scope.row.userId)" v-if="scope.row.status == 2">启用</el-button>
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

        <!-- 审核弹出框 -->
        <el-dialog title="公司审核" :visible.sync="examineVisible" width="60%">
            <el-form ref="form"  label-width="80px" style="padding: 30px;font-size:large">
                <el-row>
                    <el-col style="width: 50%">
                        <div class="user-info-list" >
                            公司名称：&emsp;&emsp;<span>广东招聘公司</span>
                        </div>
                        <div class="user-info-list" >
                            公司网站：&emsp;&emsp;<span>www.baidu.com</span>
                        </div>
                        <div class="user-info-list" >
                            最少人数：&emsp;&emsp;<span>100</span>
                        </div>
                        <div class="user-info-list" >
                            发展阶段：&emsp;&emsp;<span>上市公司</span>
                        </div>
                    </el-col>
                    <el-col style="width: 50%">
                        <div class="user-info-list" >
                            行业&emsp;&emsp;：&emsp;&emsp;<span>互联网</span>
                        </div>
                        <div class="user-info-list" >
                            省份&emsp;&emsp;：&emsp;&emsp;<span>广东省</span>
                        </div>
                        <div class="user-info-list" >
                            最多人数：&emsp;&emsp;<span>150</span>
                        </div>
                        <div class="user-info-list" >
                            公司地址：&emsp;&emsp;<span>广东省佛山市禅城区江湾9999号</span>
                        </div>
                    </el-col>
                </el-row>
                <el-row>
                    <div class="user-info-list" >
                        公司logo：&emsp;&emsp;<el-image
                            style="width: 100px; height: 100px"
                            :src="url"
                            :fit="fit"></el-image>
                    </div>
                </el-row>
                <el-row>
                    <div class="user-info-list" >
                        营业执照：&emsp;&emsp;<el-image
                            style="width: 100px; height: 100px"
                            :src="url"
                            :fit="fit"></el-image>
                    </div>
                </el-row>
                <el-row>
                    <div class="user-info-list" >
                        公司介绍：&emsp;&emsp;<span>经过多年的努力和积累，我们拥有了一支优秀的业务、技术团队和管理队伍。这个团队着力于解决客户在风险管理和金融审计、银行中间业务及渠道建设、人力资源管理、企业协同办公和信息门户等方面信息化建设的问题。我们通过分析客户多方位的需求，与客户一起打造专业、安全、完整、可行的信息技术支持平台，进而推动业务的发展和服务水平的提升，帮助客户获得更强的市场竞争力。

多年来，凭借着具有自主知识产权的产品和长期建立起的“以客户为中心”的服务形象，我们获得了市场的广泛认可，客户遍及金融、政府、电信、企业等各大领域。

中信网络科技始终追求为客户提供最好服务，为员工提供施展才能的广阔平台。

中信网络科技招贤纳士，诚邀您的加盟！走进中信网络科技、了解中信网络科技、加入中信网络科技，您绚烂的人生将从此开始！</span>
                    </div>
                </el-row>


            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="saveEdit">确 定</el-button>
                <el-button @click="examineVisible = false">取 消</el-button>
            </span>
        </el-dialog>


        <!-- 编辑弹出框 -->
        <el-dialog title="编辑" :visible.sync="editVisible" width="30%">
            <el-form ref="form" :model="form" label-width="70px">
                <el-form-item label="用户名">
                    <el-input v-model="form.name"></el-input>
                </el-form-item>
                <el-form-item label="地址">
                    <el-input v-model="form.address"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdit">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
import { fetchData } from '../../api/index';
export default {
    name: 'basetable',
    data() {
        return {
            page:{
                pages:1,
                total:1,
            },
            users:[],
            options1: [{
                value: '',
                label: '全部'
            }, {
                value: '2',
                label: '已注销'
            }, {
                value: '1',
                label: '正常使用'
            },
            ],
            options: [{
                value: '',
                label: '全部'
            }, {
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
            query: {
                status: null,
                role: null,
                name: "",
                size: 10,
                current: 1,
            },
            companyData: [
                {
                    id: 1,
                    name: "1233675",
                    registTime: '2020 1 10',
                    stage: "正常使用",
                    role:'学生',
                    ask: 1,
                    answer: 2,
                    status: 0
                },
                {
                    id: 2,
                    name: "13868787",
                    registTime: '2020 1 10',
                    stage: "已注销",
                    ask: 1,
                    answer: 2,
                    role:'学生',
                    status: 1
                },
                {
                    id: 3,
                    name: "134433434",
                    registTime: '2020 1 10',
                    stage: "已注销",
                    ask: 1,
                    answer: 2,
                    role:'教师',
                    status: 1
                },
                {
                    id: 4,
                    name: "345565467678",
                    registTime: '2020 1 10',
                    stage: "正常使用",
                    ask: 1,
                    answer: 2,
                    role:'学生',
                    status: 0
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
        this.loadData();
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
        // 重置密码操作
        handleReset(userId) {
            // 二次确认重置密码
            this.$confirm('确定要重置密码吗？', '提示', {
                type: 'warning'
            })
                .then(() => {
                    this.$patch("/User/"+userId).then(res=>{
                        this.$message.success(res.message);
                    })
                    // this.$message.success('重置密码成功');
                })
                .catch(() => {});
        },
        // 注销操作
        handleDelete(userId) {
            // 二次确认注销
            this.$confirm('确定要注销吗？', '提示', {
                type: 'warning'
            })
                .then(() => {
                    this.$patch("/User?userId="+userId,).then( res=>{
                        this.loadData();
                        this.$message.success(res.message);
                    });
                    // this.$message.success('注销用户成功');
                })
                .catch(() => {});
        },
        // 启用操作
        handleReUse(userId) {
            // 二次确认注销
            this.$confirm('确定要启用吗？', '提示', {
                type: 'warning'
            })
                .then(() => {
                    this.$patch("/User?userId="+userId,).then( res=>{
                        this.loadData();
                        this.$message.success(res.message);
                    });
                    // this.$message.success('注销用户成功');

                })
                .catch(() => {});
        },
        // 查询用户信息
        handleSearch() {
            this.loadData();
        },

        loadData(){
            this.$fetch("/User",this.query).then(res=>{
                this.users = res.data.records;
                this.page.total = res.data.total;
                this.page.pages = res.data.pages;
                this.query.size = res.data.size;

            })
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
