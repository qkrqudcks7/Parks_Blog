import axios from 'axios'

const URL = 'http://localhost:8080'

export default {
    getAllBoard (page,size) {
        return axios.get(URL + `/allboard?page=${page}&size=${size}`)
    },
    addBoard (formData) {
        return axios.post(URL + '/board', formData)
    },
    board (id) {
      return axios.get(URL + `/board/${id}`)
    },
    delete (id) {
      return axios.delete(URL + `/board/${id}`)
    },
    modify (id,BoardRequest) {
      return axios.put(URL + `/board/${id}`, BoardRequest)
    },
    search(keyword) {
      return axios.get(URL + `/search/${keyword}`)
    }
}
