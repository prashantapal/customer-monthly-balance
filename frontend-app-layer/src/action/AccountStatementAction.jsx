import React, {useEffect, useRef, useState} from "react"
import _ from "lodash"
import {encode} from "base-64"
import {Oval} from "react-loader-spinner"
import api from "../api/AccountStatementApi"
import AccountStatementView from "../view/AccountStatementView"
import "../style/accountStatement.css"
import "../style/login.css"

const AccountStatementAction = () => {
  const count = useRef(0)
  const [isLoading, setLoading] = useState(true)
  const [isAuthError, setAuthError] = useState(false)
  const [isError, setError] = useState(false)
  const [accountStatements, setAccountStatements] = useState([])
  const [token, setToken] = useState("");

  useEffect(() => {
    if (count.current !== 0 && !_.isEmpty(token)) {
      api.fetchAccountStatements(token).then(response => {
        setAccountStatements(_.orderBy(response.data, "month", "desc"))
        setLoading(false)
        setAuthError(false)
        setError(false)
      }).catch(error => {
        setLoading(false)
        if (error.response && error.response.status === 401) {
          setAuthError(true)
        } else {
          setError(true)
        }
      })
    }
    count.current++
  }, [token])

  if (_.isEmpty(token)) {
    return <Login handleSubmit={setToken}/>
  }

  if (isLoading) {
    return <Loading />
  } else if (isAuthError) {
    return <AuthError />
  } else if (isError) {
    return <Error />
  } else {
    return <AccountStatementView {...{accountStatements}} />
  }
}

const Loading = () => (
  <div className="centered">
    <Oval
      ariaLabel="loading-indicator"
      height={80}
      width={80}
      strokeWidth={3}
      strokeWidthSecondary={3}
      color="blue"
      secondaryColor="lightblue"
    />
  </div>
)

const AuthError = () => (
  <div className="centered error">
    <h3>Invalid username or password!!!</h3>
  </div>
)

const Error = () => (
  <div className="centered error">
    <h3>Internal server error!!!</h3>
  </div>
)

const Login = ({ handleSubmit }) => {
  // React States
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const setUsernameChange = (event) => setUsername(event.target.value)
  const setPasswordChange = (event) => setPassword(event.target.value)

  const handle = (event) => {
    event.preventDefault();
    if (_.isEmpty(username) || _.isEmpty(password)) {
      alert("Username and password are mandatory.")
      return false
    }
    const token = encode(username + ":" + password)
    handleSubmit(token)
  };

  return (
    <>
    <div><p className="message">Welcome to HCDL Bank personal banking</p></div>
    <div className="app">
      <div className="login-form">
        <div className="title">Please Identify Yourself</div>
        <div className="form">
          <form>
            <div className="input-container">
              <label>Username *</label>
              <input type="text" name="uname" required onChange={setUsernameChange}/>
            </div>
            <div className="input-container">
              <label>Password *</label>
              <input type="password" name="pass" required onChange={setPasswordChange}/>
            </div>
            <div className="button-container">
              <button onClick={handle}>Submit</button>
            </div>
          </form>
        </div>
      </div>
    </div>
      </>
  )
}

export default AccountStatementAction