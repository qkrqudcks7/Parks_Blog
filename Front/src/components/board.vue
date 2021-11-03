<template>
  <body>
    <section>
      <h1>{{myBoard.subject}}</h1>
      <hr>
      <input class="url" type="text" id="urlInput" />
      <div class="view">
        <div>조회 {{myBoard.viewCount}}</div>
        <div>스크랩 {{myBoard.trackBackCount}}</div>
        <div>댓글 {{commentList.length}}</div>
      </div>
      <div class="tool">
        <div>
          {{myBoard.time}}
        </div>
        <div class="util">
          <span @click="copyUrl">URL 복사</span>&nbsp;&nbsp;
          <span @click="goModify">수정</span> &nbsp;&nbsp;
          <span @click="deleteBoard">삭제</span>
        </div>
      </div>
      <div class="row">
        <div class="columns">
          <img v-bind:src="myBoard.image" />
        </div>
        <div class="columns">
          <div class="third">
            <label>카테고리</label>
            <div>{{myBoard.category}}</div>
          </div>
          <div class="third">
            <label>짧은 소개</label>
            <div>{{myBoard.shortDescription}}</div>
          </div>
        </div>
      </div>
      <div class="row">
        <label>게시글 소개</label>
        <div class="ckeditor">
          <ckeditor type="inline" v-model="myBoard.text" readOnly></ckeditor>
        </div>
      </div>
      <br>
      <label>전체 댓글 {{commentList.length}}개</label>
      <hr>
      <div class="comments">
        <div class="letter" v-for="(i,index) in commentList" :key="index + 'a'">
          <div class="user">{{i.localDateTime}} &nbsp;익명</div>
          <div class="com">{{i.comment}}</div>
        </div>
      </div>
      <div class="comment">
        <textarea class="insert" v-model="commentsRequest.comment" name="comment" cols="20" v-validate="'required'"></textarea>
        <div class="alert alert-danger" role="alert" v-if="errors.has('comment')">댓글을 입력해주세요!</div>
        <button class="upload" @click="addComment" @keyup.enter="addComment">등록</button>
      </div>
    </section>
  </body>
</template>

<script>
import board from '../API/board'
import comments from '../API/comments'
import notification from "../custom/notification";

export default {
  name: "board",
  data () {
    return {
      myBoard: [],
      commentsRequest: {
        boardId: this.boardId,
        comment: "",
      },
      commentList: []
    }
  },
  computed: {
    boardId () {
      return this.$route.params.id
    }
  },
  methods: {
    deleteBoard () {
      if (confirm("삭제 하시겠습니까?")) {
        board.delete(this.myBoard.id).then(response => {
          if (response.status === 200) {
            alert("삭제되었습니다.")
            this.$router.push('/')
          }
        })
      }
    },
    goModify () {
      this.$router.push({name: 'ModifyBoard', params: {id: this.boardId}})
    },
    async addComment () {
      await this.$validator.validateAll()
      if (this.errors.any()) {
        return
      }
      try {
        this.commentsRequest.boardId = this.boardId
        const result = await comments.addComment(this.commentsRequest)
        notification.success(result, "등록했습니다", () => {
          this.$router.go(this.$router.currentRoute)
        })
      } catch (error) {
        this.$notify({
          group: 'noti',
          type: 'error',
          duration: 6000,
          title: '작성 실패',
          text: '빈칸으로 입력하지마세요!'
        })
      }
    },
    copyUrl () {
      alert("주소가 복사되었습니다. 해당 주소를 Ctrl + V를 눌러 입력하세요")
      const urlInput = document.getElementById("urlInput")
      urlInput.value = window.document.location.href
      urlInput.select(); document.execCommand("copy")
      urlInput.eventUrlInput.blur()
    }

  },
  mounted() {
    board.board(this.boardId).then(response => {
      if (response.status === 200) {
        this.myBoard = response.data
      }
    })

    comments.getAllComments(this.boardId).then(response => {
      if (response.status === 200) {
        this.commentList = response.data
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
.url {
  opacity: 0;
}
.view {
  display: flex;
  justify-content: left;
  margin-bottom: 10px;
}
.view div {
  padding: 0 10px;
}
.view div:first-child {
  padding: 0;
}
.tool {
  display: flex;
  justify-content: space-between;
}
.tool .util{
  text-align: right;
}
.tool span:hover {
  color: #0a58ca;
  cursor: pointer;
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
  background-color: crimson;
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
.comments {
  margin: 30px 0;
}
.comments .letter {
  display: flex;
  margin-bottom: 30px;
}
.comments .letter .user {
  flex: 1;
  margin-right: 10px;
}
.comments .letter .com {
  flex: 4;
}
.comment {
  margin: 30px 0;
  padding: 10px;
  display: flex;
}
.comment .insert {
  flex: 5;
  margin-right: 10px;
}
.comment .upload {
  flex: 1;
  border-radius: 8px;
}
@media (max-width: 768px) {
  section {
    width: 100%;
  }
}
</style>
