<template>
  <body>
    <section>
      <div class="name">
        <span>Park's</span> 블로그 통계
      </div>
      <div class="total">
        <div class="board">총 게시글 {{total.board}}</div>
        <div class="comment">총 댓글 {{total.comment}}</div>
        <div class="scrap">총 스크랩 {{total.scrap}}</div>
        <div class="view">총 조회수 {{total.view}}</div>
      </div>
      <div class="name">
        <span>Park's</span> 한눈에 보기
      </div>
      <div class="items">
        <div v-for="(i,index) in board.adminBoardResponses" :key="index + 'a'">
          <div class="item">
            <img v-if="i.image === null" src="../assets/temp.png" alt="" @click="goBoard(i.id)">
            <img v-else :src="i.image" alt="" @click="goBoard(i.id)">
            <div class="desc">
              <h6>{{i.subject}}</h6>
              <div class="like">
              <span>
                {{i.shortDescription}}
              </span>
                <span>
                조회수 {{i.viewCount}}
              </span>
              </div>
            </div>
          </div>
          <div class="comment">
            <label>전체 댓글 {{i.comments.length}}</label>
            <hr>
            <div class="comments">
              <div v-for="(j,index) in i.comments" :key="index" class="c">
                <div class="one">{{j}}</div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="page">
        <pagination :records="board.total" v-model="page" :per-page="size" @paginate="findAll" />
      </div>
    </section>
  </body>
</template>

<script>
import admin from '../API/admin'
import Pagination from 'vue-pagination-2'

export default {
  name: "admin",
  data () {
    return {
      board: [],
      total: [],
      page: 1,
      size: 9
    }
  },
  components: {
    Pagination
  },
  methods: {
    async findAll () {
      const response = await admin.getAllBoard(this.page-1,this.size)
      if (response.status === 200) {
        console.log(response.data)
        this.board = response.data
      }
    },
    async getTotal () {
      const response = await admin.getTotalInfo()
      if (response.status === 200) {
        this.total = response.data
        console.log(this.total)
      }
    },
    goBoard(id) {
      this.$router.push({name: 'Board', params: {id: id}})
    },
  },
  mounted() {
    this.findAll()
    this.getTotal()
  }
}
</script>

<style scoped>
body {
  color: black;
  margin: 0;
}
h1,h2,h3,h4,h5,h6 {
  margin: 0;
  font-weight: normal;
}
a {
  text-decoration: none;
  color: #eeeeee;
}
input,textarea,select {
  outline: none;
}
section {
  width: 950px;
  margin: auto;
}
.name {
  margin: 60px 0;
  text-align: left;
  font-size: 32px;
}
.name span {
  color: #d442f5;
}
.total {
  display: flex;
  justify-content: space-between;
  margin: 60px 0;
}
.items {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  margin-top: 60px;
}
.item {
  margin-bottom: 30px;
  width: 310px;
  height: 300px;
  overflow: hidden;
  position: relative;
}
.item:hover{
  opacity: 0.5;
}
.item img {
  width: 100%;
  height: 100%;
}
.desc {
  position: absolute;
  background-color: rgba(0,0,0,0.3);
  width: 100%;
  height: 60px;
  left: 0;
  bottom: 0;
  color: #eeeeee;
  padding: 10px;
  box-sizing: border-box;
  transition: 0.3s;
}
.desc h6 {
  font-size: 15px;
  margin-bottom: 5px;
}
.like {
  display: flex;
  justify-content: space-between;
}
.like span {
  font-size: 13px;
}
.page {
  display: flex;
  justify-content: center;
}
.comments {
  margin: 30px 0;
  background: #dddddd;
}
.c {
  margin: 15px 0;
  display: flex;
  justify-content: space-between;
}
.c span:hover {
  color: #0a58ca;
  cursor: pointer;
}
@media (max-width: 768px) {
  section {
    width: 100%;
    padding: 20px;
    box-sizing: border-box;
  }
  .search-box input {
    width: 100%;
    margin-bottom: 20px;
    box-sizing: border-box;
  }
  .item {
    width: 100%;
  }
}
</style>
