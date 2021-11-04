<template>
  <body>
  <section>
    <div class="name">
      <span>Park's</span> 블로그에 오신 여러분 환영합니다. 🎉
    </div>
    <div class="search-box">
      <input type="text" placeholder="검색하세요" v-model="word" @keyup.enter="search">
      <button @click="search">검색</button>
    </div>
    <div class="topView">
      <div class="name">
        <span>🔥 Top 3 Park's feed</span>
      </div>
      <div class="feed">
        <div class="one" @click="goBoard(viewBoard[0].id)">📌 {{viewBoard[0].subject}} [조회수 {{viewBoard[0].viewCount}}]</div>
        <div class="two" @click="goBoard(viewBoard[1].id)">📌 {{viewBoard[1].subject}} [조회수 {{viewBoard[1].viewCount}}]</div>
        <div class="three" @click="goBoard(viewBoard[2].id)">📌 {{viewBoard[2].subject}} [조회수 {{viewBoard[2].viewCount}}]</div>
      </div>
    </div>

    <div class="name">
      <span>🗃️ Park's Blog</span>
    </div>
    <div class="items">
      <div class="item" v-for="(i,index) in board.boardResponses" :key="index">
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
    </div>
    <div class="page">
      <pagination :records="board.total" v-model="page" :per-page="size" @paginate="findAll" />
    </div>
  </section>
  </body>
</template>

<script>
import board from '../API/board'
import Pagination from 'vue-pagination-2'

export default {
  name: 'Home',
  data () {
    return {
      board: [],
      viewBoard: [],
      word: '',
      page: 1,
      size: 9
    }
  },
  components: {
    Pagination
  },
  methods: {
    async findAll () {
      const response = await board.getAllBoard(this.page-1,this.size)
      if (response.status === 200) {
        this.board = response.data
      }
    },
    goBoard(id) {
      this.$router.push({name: 'Board', params: {id: id}})
    },
    search () {
      board.search(this.word).then(response => {
        if (response.status === 200) {
          this.board = response.data
        }
      })
    },
    async getByViewCount () {
      const response = await board.getViewCount()
      if (response.status === 200) {
        this.viewBoard = response.data
      }
    }
  },
  mounted() {
    this.findAll()
    this.getByViewCount()
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
  margin: 60px;
  text-align: center;
  font-size: 32px;
}
.name span {
  color: #d442f5;
}

.search-box {
  display: flex;
  justify-content: space-between;
}
.search-box input {
  flex: 3;
  border: none;
  border-bottom: 1px solid #dddddd;
  font-size: 18px;
  padding: 5px;
  background: url(../assets/search.png) no-repeat center right;
  background-size: 22px;
  margin-right: 40px;
}
.search-box button {
  flex: 1;
  border: none;
  background-color: #d442f5;
  color: white;
  padding: 15px 30px;
  border-radius: 6px;
  font-size: 20px;
  box-shadow: 5px 5px 20px black;
}
.topView .name {
  margin: 60px 0;
  text-align: left;
  font-size: 32px;
}
.topView .feed {
  color: #0a58ca;
  cursor: pointer;
}
.topView .one {
  margin-bottom: 15px;
}
.topView .two {
  margin-bottom: 15px;
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
@media (max-width: 768px) {
  section {
    width: 100%;
    padding: 20px;
    box-sizing: border-box;
  }
  .search-box {
    flex-direction: column;
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
