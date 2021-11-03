import axios from 'axios'

const URL = 'http://localhost:8080'

export default {
  getAllComments (id) {
    return axios.get(URL + `/comments/${id}`)
  },
  addComment (CommentsRequest) {
    return axios.post(URL + '/comments', CommentsRequest)
  }
}
