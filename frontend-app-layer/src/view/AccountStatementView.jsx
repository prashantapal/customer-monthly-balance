import React from 'react';
import Table from 'react-bootstrap/Table';
import moment from "moment";

const AccountStatementView = ({ accountStatements }) => (
  <>
    {accountStatements.map(accountStatement =>
      <Table>
        <tbody>
        <tr>
          <td>{monthlyTransaction(accountStatement)}</td>
          <td><MonthlyClosingBalance {...{accountStatement}} /></td>
        </tr>
        </tbody>
      </Table>
    )}
  </>
)

const monthlyTransaction = (accountStatement, showTableHeader = false) => (
  <>
    <Table>
      {showTableHeader &&
        <thead>
          <tr>
            <th>Transaction</th>
            <th>Closing balance(cumulative)</th>
          </tr>
        </thead>
      }
        <tbody>
          <tr>
            <td>{accountTransactions(accountStatement.transactions)}</td>
            <td></td>
          </tr>
        </tbody>
    </Table>
  </>
)

const MonthlyClosingBalance = ({ accountStatement }) => (
  <>
    <tr>
      <td>{moment(accountStatement.month).format("MMMM, YYYY")}  Total credit: {accountStatement.totalCredit.toFixed(2)}   Total debit: {accountStatement.totalDebit.toFixed(2)}   Closing balance: {accountStatement.balance.toFixed(2)}</td>
      <td>{accountStatement.cumulativeBalance.toFixed(2)}</td>
    </tr>
  </>
)

const accountTransactions = (accountTransactions, showTableHeader = false) => (
  <>
    <Table>
      {showTableHeader &&
        <thead>
          <tr>
            <th>Transaction Date</th>
            <th>Transaction Id</th>
            <th>Description</th>
            <th>Amount</th>
          </tr>
        </thead>
      }
      <tbody>
        {accountTransactions.map(transaction => <Transaction  {...{transaction}} />)}
      </tbody>
    </Table>
  </>
)

const Transaction = ({ transaction }) => (
  <>
    <tr>
      <td>{moment(transaction.transactionDate).format("DD.MM.YYYY HH:mm:ss")}</td>
      <td>{transaction.transactionId}</td>
      <td>{transaction.transactionDescription}</td>
      {isCreditType(transaction) ?
        <td>(+){transaction.transactionAmount.toFixed(2)}</td>
        : <td>(-){transaction.transactionAmount.toFixed(2)}</td>}
    </tr>
  </>
)

const isCreditType = (accountTransaction) => accountTransaction.transactionType === "CREDIT";

export default AccountStatementView;