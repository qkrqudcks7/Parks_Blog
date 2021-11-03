<template>
  <body>
  <section>
    <h1><input type="text" name="subject" v-model="myBoard.subject" v-validate="'required'"></h1>
    <div class="alert alert-danger" role="alert" v-if="errors.has('subject')">제목을 입력해주세요!</div>
    <div class="tool">
      <div>
        {{myBoard.time}}
      </div>
    </div>
    <div class="row">
      <div class="columns">
        <img v-bind:src="myBoard.image" />
      </div>
      <div class="columns">
        <div class="third">
          <label>카테고리</label>
          <input type="text" name="category" v-model="myBoard.category" v-validate="'required'">
          <div class="alert alert-danger" role="alert" v-if="errors.has('category')">카테고리를 입력해주세요!</div>
        </div>
        <div class="third">
          <label>짧은 소개</label>
          <input type="text" name="shortDescription" v-model="myBoard.shortDescription" v-validate="'required'">
          <div class="alert alert-danger" role="alert" v-if="errors.has('shortDescription')">소개글을 입력해주세요!</div>
        </div>
      </div>
    </div>
    <div class="row">
      <label>게시글 소개</label>
      <div class="ckeditor">
        <ckeditor v-model="myBoard.text"></ckeditor>
      </div>
    </div>
    <div class="row" >
      <button @click="modify">수정하기</button>
    </div>
  </section>
  </body>
</template>

<script>
import board from '../API/board'
import notification from "../custom/notification";

export default {
  name: "modifyBoard",
  data () {
    return {
      myBoard: []
    }
  },
  computed: {
    boardId () {
      return this.$route.params.id
    }
  },
  methods: {
    async modify () {
      await this.$validator.validateAll()
      if (this.errors.any()) {
        return
      }
      try {
        const result = await board.modify(this.boardId, this.myBoard)
        notification.success(result, '수정 성공', () => {
          this.$router.push({name: 'Board', params: {id: this.boardId}})
        })
      } catch (error) {
        this.$notify({
          group: 'noti',
          type: 'error',
          duration: 6000,
          title: '수정 실패',
          text: '변경상 오류가 생겼습니다.'
        })
      }
    }
  },
  mounted() {
    board.board(this.boardId).then(response => {
      if (response.status === 200) {
        this.myBoard = response.data
        console.log(this.myBoard)
      }
    })
  }
}
</script>

<style scoped>
body {
  margin: 0px;
  font-size: 15px;
  font-weight: 300;
}
h1,h2,h3,h4,h5,h6 {
  margin: 0;
  font-weight: normal;
}
h1 {
  text-align: center;
  margin: 30px;
}
button {
  cursor: pointer;
  outline: none;
}
input {
  outline: none;
}
section {
  width: 950px;
  margin: auto;
}
.tool {
  display: flex;
  justify-content: space-between;
}
.row {
  display: flex;
  margin-top: 30px;
}
.row label {
  margin-bottom: 10px;
  padding: 10px;
  font-weight: bold;
}
.row .columns:nth-child(1) {
  flex: 1;
  padding: 20px;
}
.row .columns:nth-child(2) {
  flex: 3;
  display: flex;
  flex-direction: column;
  justify-content: center;
  padding: 20px;
}
.row button {
  padding: 20px;
  border: none;
  border-radius: 8px;
  background-color: #d442f5;
  color: white;
}
.third {
  display: flex;
  margin-bottom: 20px;
  justify-content: space-between;
}
.third label {
  flex: 1;
  text-align: center;
  font-weight: bold;
}
.third div {
  flex: 3;
  padding: 8px;
}
.third input {
  flex: 3;
}
.columns img {
  width: 100%;
}
.ckeditor {
  border: 1px solid black;
  border-radius: 5px;
}
@media (max-width: 768px) {
  section {
    width: 100%;
  }
}
</style>
