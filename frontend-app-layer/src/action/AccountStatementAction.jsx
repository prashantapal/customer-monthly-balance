import { useEffect, useRef, useState } from 'react'
import _ from 'lodash'
import { Oval } from 'react-loader-spinner'
import api from '../api/AccountStatementApi'
import AccountStatementView from "../view/AccountStatementView"
import '../style/AccountStatement.css'

const AccountStatementAction = () => {
  const count = useRef(0)
  const [isLoading, setLoading] = useState(true)
  const [isAuthError, setAuthError] = useState(false)
  const [isError, setError] = useState(false)
  const [accountStatements, setAccountStatements] = useState([])

  useEffect(() => {
    if (count.current !== 0) {
      api.fetchAccountStatements().then(response => {
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
  }, [])

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
  <div className="centered">
    <h3>Invalid username or password!!!</h3>
  </div>
)

const Error = () => (
  <div className="centered">
    <h3>Internal server error!!!</h3>
  </div>
)
export default AccountStatementAction