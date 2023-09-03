import axios from "axios"

const baseURL = process.env.REACT_APP_REST_URL

class AccountStatementApi {

  fetchAccountStatements(token) {
    return axios.get(baseURL + "/account-statement", {
      headers: this._getAuthorizationHeader(token)
    })
  }

  _getAuthorizationHeader(token) {
    return {
      Authorization: "Basic " + token
    }
  }

}
export default new AccountStatementApi();