import Vue from 'vue'
import Router from 'vue-router'
import Home from '../components/home'
import AddBoard from '../components/addBoard'
import Board from '../components/board'
import ModifyBoard from '../components/modifyBoard'

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Home
    },
    {
      path: '/addboard',
      name: 'AddBoard',
      component: AddBoard
    },
    {
      path: '/board:id',
      name: 'Board',
      component: Board
    },
    {
      path: '/modifyBoard:id',
      name: 'ModifyBoard',
      component: ModifyBoard
    }
  ]
})
