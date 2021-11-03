<template>
  <div id="app">
    <notifications group="noti" position = "bottom center" />
    <app-header />
    <router-view/>
  </div>
</template>

<script>
import appHeader from './components/header'
// import notification from './custom/notification'
export default {
  name: 'App',
  components: {
    appHeader
  }
}
</script>

<style>
</style>
