<template>
  <body>
    <section>
      <div class="name">
        <span>Park's</span> 블로그의 통계
      </div>
      <div class="total">
        <div class="board">총 게시글 10</div>
        <div class="comment">총 댓글 10</div>
        <div class="scrap">총 스크랩 10</div>
        <div class="view">총 조회수 200</div>
      </div>
      <div class="name">
        <span>Park's</span> 댓글 관리
      </div>
    </section>
  </body>
</template>

<script>
export default {
  name: "admin"
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
  width: 1350px;
  margin: auto;
  background-color: #dddddd;
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
</style>
