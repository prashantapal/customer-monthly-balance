import React from 'react';
import moment from "moment";

const AccountStatementView = (accountStatements) => {

}

const AccountTransactions = (accountTransactions) => (
  <>
    <table>
      <tr>
        {accountTransactions.map(accountTransaction => <Transaction transaction = {accountTransaction} />)}
      </tr>
    </table>
  </>
)

const Transaction = (transaction) => (
  <>
    <td>{moment(transaction.transactionDate).format("DD.MM.YYYY HH:mm:ss")}</td>
    <td>{transaction.transactionId}</td>
    <td>{transaction.transactionDescription}</td>
    {isCreditType(transaction) ?
      <td>(+){transaction.transactionAmount}</td>
      : <td>(-){transaction.transactionAmount}</td>}
  </>
)

const isCreditType = (accountTransaction) => accountTransaction.transactionType === "CREDIT";