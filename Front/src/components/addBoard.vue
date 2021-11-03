<template>
  <body>
    <section>
      <div class="board-header">
        <h1>
          <span>블로그</span>를 작성해주세요!
        </h1>
      </div>
      <div class="study-form">
        <div class="row">
          <div class="field">
            <label>트랙백</label>
            <input type="text" name="category" v-model="boardRequest.url" placeholder="다른 글 트랙백을 원하시면 링크를 입력해주세요! 원하지 않으면 비어도 됩니다.">
          </div>
        </div>
        <div class="row">
          <div class="field">
            <label>카테고리<em>*</em></label>
            <input type="text" name="category" v-model="boardRequest.category" placeholder="15자 이내로 써주세요" v-validate="'required'">
            <div class="alert alert-danger" role="alert" v-if="errors.has('category')">카테고리를 입력해주세요!</div>
          </div>
        </div>
        <div class="row">
          <div class="field">
            <label>제목<em>*</em></label>
            <input type="text" name="title" v-model="boardRequest.subject" placeholder="30자 이내로 써주세요" v-validate="'required'">
            <div class="alert alert-danger" role="alert" v-if="errors.has('title')">제목을 입력해주세요!</div>
          </div>
        </div>
        <div class="row">
          <div class="field">
            <label for="img">배경 이미지</label>
            <input type="file" id="img" ref="bgimg" @change="selectFile" accept="image/*" />
          </div>
        </div>
        <div class="row">
          <div class="field">
            <label>간단소개<em>*</em></label>
            <textarea rows="2" v-model="boardRequest.shortDescription" placeholder="스터디 배너에 걸릴 간단한 소개를 입력해주세요." v-validate="'required'" name="short"></textarea>
            <div class="alert alert-danger" role="alert" v-if="errors.has('short')">간단한 소개를 입력하세요!</div>
          </div>
        </div>
        <div class="row">
          <div class="field">
            <label>긴 소개<em>*</em></label>
            <ckeditor v-model="boardRequest.text" :config="editorConfig" v-validate="'required'"></ckeditor>
          </div>
        </div>
        <button class="btn" @click="addBoard">업로드</button>
      </div>
    </section>
  </body>
</template>

<script>
import notification from "../custom/notification";
import board from '../API/board'

export default {
  name: "addBoard",
  data () {
    return {
      editorConfig:{},
      img: '',
      boardRequest: {
        subject: '',
        shortDescription: '',
        text: '',
        category: ''
      }
    }
  },
  methods: {
    async addBoard () {
      await this.$validator.validateAll()
      if (this.errors.any()) {
        return
      }
      try {
        const formData = new FormData()
        formData.append('subject',this.boardRequest.subject)
        formData.append('shortDescription',this.boardRequest.shortDescription)
        formData.append('text',this.boardRequest.text)
        formData.append('category',this.boardRequest.category)
        formData.append('url',this.boardRequest.url)
        formData.append('multipartFile', this.img)
        const response = await board.addBoard(formData)
        notification.success(response,'업로드 되었습니다.', () => {
          this.$router.push('/')
        })
      } catch (err) {
        notification.error(err, '올바른 정보를 입력해주세요', () => {
          this.$router.push("/addboard")
        })
      }
    },
    selectFile () {
      this.img = this.$refs.bgimg.files[0]
    }
  }
}
</script>

<style scoped>
body {
  margin: 0;
  font-size: 15px;
  font-weight: 300;
}
section {
  width: 950px;
  margin: auto;
}
h1,h2,h3,h4,h5,h6 {
  margin: 0;
  font-weight: normal;
}
button {
  cursor: pointer;
  outline: none;
}
input, textarea, select {
  outline: none;
}
.board-header {
  margin: 30px;
  text-align: center;
}
.board-header h1 {
  font-size: 32px;
}
.board-header h1 span {
  color: crimson;
}
.board-header hr {
  border: none;
  height: 3px;
  background-color: skyblue;
  margin: 25px auto;
}
.study-form {
  margin-top: 30px;
}
.study-form label {
  font-weight: bold;
  margin-bottom: 5px;
}
.study-form label em {
  color: red;
}
.row {
  display: flex;
  margin: 10px 0;
}
.row .field {
  display: flex;
  flex-direction: column;
  flex: 1;
  margin-bottom: 30px;
}
.row .field input, .row .field select {
  margin-bottom: 5px;
}
.row .field input:hover,
.row .field textarea:hover {
  border: 1px solid skyblue;
  box-shadow: 0 0 5px skyblue;
}
.row .field input:focus,
.row .field textarea:focus {
  border: 1px solid skyblue;
  box-shadow: 0 0 5px skyblue;
}
.row .field input,
.row .field textarea {
  padding: 7px;
  border-radius: 3px;
  transition: 0.3s;
}
.btn {
  width: 97.5%;
  border: 1px solid #555;
  background-color:  #74b9ff;
  color: white;
  padding: 7px 50px;
  display: block;
  margin: auto auto 20px;
}
.btn:hover {
  border: 1px solid skyblue;
  box-shadow: 0 0 5px skyblue;
}
#img {
  border: 1px solid black;
}
@media (max-width: 768px) {
  section {
    width: 100%;
    padding: 20px;
    box-sizing: border-box;
  }
  .board-header {
    font-size: 20px;
  }
  .row {
    flex-direction: column;
  }
  .row .field {
    margin: 10px 0;
  }
  .btn {
    width: 95%;
  }
}
</style>
