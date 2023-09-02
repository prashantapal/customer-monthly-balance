import { useEffect, useState } from 'react';
import { Oval } from 'react-loader-spinner';
import api from '../api/AccountStatementApi';
import AccountStatementView from "../view/AccountStatementView";

const AccountStatementAction = () => {
  const [isLoading, setLoading] = useState(true);
  const [isAuthError, setAuthError] = useState(false);
  const [isError, setError] = useState(false);
  const [accountStatements, setAccountStatements] = useState([]);

  useEffect(() => {
    api.fetchAccountStatements().then(response => {
      console.log(response.data)
      setAccountStatements(response.data)
      setLoading(false)
      setAuthError(false)
      setError(false)
    }).catch(error => {
      console.log(error)
      setLoading(false)
      if (error.response && error.response.status === 401) {
        setAuthError(true)
      } else {
        setError(true)
      }
    })
  }, []);

  if (isLoading) {
    return <Loading />;
  } else if (isAuthError) {
    return <AuthError />;
  } else if (isError) {
    return <Error />
  } else {
    return <AccountStatementView {...{accountStatements}} />
  }
}

const Loading = () => (
  <div className="App">
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
);

const AuthError = () => (
  <div className="App">
    <h3>Invalid username or password!!!</h3>
  </div>
);

const Error = () => (
  <div className="App">
    <h3>Internal server error!!!</h3>
  </div>
);
export default AccountStatementAction;