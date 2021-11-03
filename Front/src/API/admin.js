import axios from 'axios'

const URL = 'http://localhost:8080'

export default {
  getAllBoard (page,size) {
    return axios.get(URL + `/alladminboard?page=${page}&size=${size}`)
  },
  getTotalInfo () {
    return axios.get(URL + '/total')
  },
}
