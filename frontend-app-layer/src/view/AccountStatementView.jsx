import React from 'react'
import Table from 'react-bootstrap/Table'
import moment from "moment"

const AccountStatementView = ({ accountStatements }) =>(
  <div className="centered centered-height">
    <Table>
      {accountStatements.map((eachMonthStatement, index) =>
        <tbody key={moment(eachMonthStatement.month).format("YYYYMMDD")}>
        <tr>
          <td>{monthlyStatement(eachMonthStatement, index === 0)}</td>
        </tr>
        </tbody>
      )}
    </Table>
  </div>
)

const monthlyStatement = (eachMonthStatement, showTableHeader) => (
  <Table className="monthly-statement">
    <tbody>
    <tr>
      <td>{monthlyTransactions(eachMonthStatement, showTableHeader)}</td>
    </tr>
    </tbody>
  </Table>
)

const MonthlyClosingBalance = ({ eachMonthStatement }) => (
  <>
    <Table className="monthly-balance">
      <tbody>
      <tr>
        <td>{moment(eachMonthStatement.month).format("MMMM, YYYY")}  Total credit: {eachMonthStatement.totalCredit.toFixed(2)}   Total debit: {eachMonthStatement.totalDebit.toFixed(2)}   Closing balance: {eachMonthStatement.balance.toFixed(2)}</td>
        <td>{eachMonthStatement.cumulativeBalance.toFixed(2)}</td>
      </tr>
      </tbody>
    </Table>
  </>
)

const monthlyTransactions = (eachMonthStatement, showTableHeader) => (
  <>
    <Table className="transaction">
      {showTableHeader &&
        <thead>
        <tr>
          <th>Transaction Date</th>
          <th>Transaction Id</th>
          <th>Description</th>
          <th>Amount</th>
          <th>Closing Balance(Cumulative)</th>
        </tr>
        </thead>
      }
      <tbody>
      {eachMonthStatement.transactions.map((transaction, index) =>
        <Transaction key={transaction.transactionId} index={index}
                     numberOfTransactions={eachMonthStatement.transactions.length}
                     transaction={transaction}
                     monthlyCumulativeBalance={eachMonthStatement.cumulativeBalance}/>)}
      </tbody>
    </Table>
  </>
)

const Transaction = ({index, numberOfTransactions, transaction, monthlyCumulativeBalance}) => {
  const {transactionId, transactionDate, transactionDescription, transactionType, transactionAmount} = transaction
  return (
    <tr>
      <td>{moment(transactionDate).format("DD.MM.YYYY HH:mm:ss")}</td>
      <td>{transactionId}</td>
      <td>{transactionDescription}</td>
      <td><TransactionAmount {...{transactionType, transactionAmount}}/></td>
      {index === 0 && <td rowSpan={numberOfTransactions}>{monthlyCumulativeBalance.toFixed(2)}</td>}
    </tr>
  )
}

const TransactionAmount = ({ transactionType, transactionAmount }) => (
  isCreditType(transactionType) ?
    <div className="credit">(+){transactionAmount.toFixed(2)}</div>
    : <div className="debit">(-){transactionAmount.toFixed(2)}</div>
)

const isCreditType = (transactionType) => transactionType === "CREDIT";

export default AccountStatementView;