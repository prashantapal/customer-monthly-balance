import { useEffect, useState } from 'react';
import api from "../api/AccountStatementApi"

const AccountStatementAction = () => {
  const [isLoading, setLoading] = useState(true);
  const [isLoaded, setLoaded] = useState(false);
  const [isError, setError] = useState(false);
  const [accountStatements, setAccountStatements] = useState([]);

  useEffect(() => {
    api.fetchAccountStatements().then(response => {
      console.log(response.data)
      setAccountStatements(response.data)
      setLoading(false)
      setLoaded(true)
    }).catch(error => {
      console.log(error)
      setError(true)
    })
  }, []);
}
export default AccountStatementAction;