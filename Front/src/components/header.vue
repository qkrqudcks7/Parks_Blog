<template>
  <header>
    <nav>
      <div class="logo">
        <a href="/">Park's Blog</a>
      </div>
    </nav>
    <ul class="gnb">
      <li><a href="/addboard">블로그 작성</a></li>
      <li><a href="/admin">관리자 페이지</a></li>
    </ul>
  </header>
</template>

<script>
export default {
  name: "hheader"
}
</script>

<style scoped>
a {
  text-decoration: none;
  color: #222;
}
h1,h2,h3,h4,h5,h6 {
  margin: 0;
}
button {
  cursor: pointer;
  outline: none;
}
header {
  background-color: #d442f5;
  display: flex;
  justify-content: center;
  height: 60px;
  align-items: center;
}
nav {
  width: 1280px;
  display: flex;
  justify-content: space-between;

}
.logo a{
  color: white;
  font-weight: bold;
  position: relative;
  padding-bottom: 8px;
}
.logo a:after {
  content: '';
  position: absolute;
  width: 0;
  height: 2px;
  background-color: #eeeeee;
  transform: translateX(-50%);
  left: 50%;
  bottom: 0;
  transition: 0.3s;
}
.logo a:hover:after {
  width: 100%;
}
.logo img {
  filter: invert();

}
.gnb {
  margin: 0;
  padding: 0;
  list-style: none;
  display: flex;
}
.gnb li {
}
.gnb li a{
  margin: 10px 50px;
  color: white;
  position: relative;
  padding-bottom: 8px;
}
.gnb li a:after {
  content: '';
  position: absolute;
  width: 0;
  height: 2px;
  background-color: #eeeeee;
  transform: translateX(-50%);
  left: 50%;
  bottom: 0;
  transition: 0.3s;
}
.gnb li a:hover:after{
  width: 100%;
}
.sns {
  text-align: center;
}
.sns a {
  color: white;
  margin: 5px 20px;
  position: relative;
  padding-bottom: 8px;
}
.sns a:after {
  content: "";
  position: absolute;
  width: 0;
  height: 2px;
  background-color: #eeeeee;
  transform: translateX(-50%);
  left: 50%;
  bottom: 0;
  transition: 0.3s;
}
.sns a:hover:after {
  width: 100%;
}

@media (max-width: 768px) {
  header {
    height: auto;
  }
  nav {
    width: 100%;
    flex-direction: column;
    position: relative;
  }
  .logo {
    padding: 10px;
  }
  .gnb {
    flex-direction: column;
    text-align: center;
    border-top: 1px solid rgba(0,0,0,0.1);
  }
  .gnb li a{
    display: block;
    padding: 8px;
    margin: 0px;
  }
}
</style>
