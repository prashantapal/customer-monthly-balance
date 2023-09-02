import axios from "axios"

const baseURL = process.env.REACT_APP_REST_URL

const headers = {
  Authorization: 'Basic ' + process.env.REACT_APP_AUTH_KEY
}

const auth = {
  username: process.env.REACT_APP_REST_USERNAME,
  password: process.env.REACT_APP_REST_PASSWORD
}

class AccountStatementApi {

  fetchAccountStatements() {
    return axios.get(baseURL + "/account-statement", {
        headers: headers
      }
    )
  }

  _getAuth(username, password) {
    return {
      username: username,
      password: password
    }
  }

}
export default new AccountStatementApi();