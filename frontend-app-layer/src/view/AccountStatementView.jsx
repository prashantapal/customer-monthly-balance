import React from "react"
import _ from "lodash"
import Table from "react-bootstrap/Table"
import moment from "moment"
import "../style/accountStatement.css"

const AccountStatementView = ({ accountStatements }) => (
  <>
    <Heading {...{accountStatements}} />
    <AccountStatements {...{accountStatements}} />
  </>
)

const Heading = ({ accountStatements }) => {
  const periodStartDate = moment(_.last(accountStatements).month).format("DD-MM-YYYY")
  const periodEndDate = moment(_.head(accountStatements).month).endOf("month").format("DD-MM-YYYY")
  return (
    <div className="wrapper">
      <p className="heading">Your account statements from {periodStartDate} to {periodEndDate}.</p>
      <p className="currency">* All amounts are in EUR</p>
    </div>
  )
}

const AccountStatements = ({ accountStatements }) => (
  <div className="account-statement">
    <Table className="transaction">
      <thead>
        <tr>
          <th>Month</th>
          <th>Transaction Date</th>
          <th>Transaction Id</th>
          <th>Description</th>
          <th>Amount</th>
          <th>Total Monthly Credit</th>
          <th>Total Monthly Debit</th>
          <th>Monthly Closing Balance</th>
          <th>Closing Balance(Cumulative)</th>
        </tr>
      </thead>
      <tbody>
        {accountStatements.map(accountStatement => monthlyStatement(accountStatement))}
      </tbody>
    </Table>
  </div>
)

const monthlyStatement = (accountStatement) => (
  accountStatement.transactions.map(
    (transaction, index) =>
      <Transaction key={transaction.transactionId} index={index}
                   transaction={transaction}
                   accountStatement={accountStatement} />
  )
)

const Transaction = ({index, transaction, accountStatement}) => {
  const {transactionId, transactionDate, transactionDescription, transactionType, transactionAmount} = transaction
  const {month, totalCredit, totalDebit, balance, cumulativeBalance} = accountStatement
  const noOfRowsGrouped = accountStatement.transactions.length
  return (
    <tr>
      {index === 0 && <td rowSpan={noOfRowsGrouped}>{moment(month).format("MMMM, YYYY")}</td>}
      <td>{moment(transactionDate).format("DD.MM.YYYY HH:mm:ss")}</td>
      <td>{transactionId}</td>
      <td>{transactionDescription}</td>
      <td><TransactionAmount {...{transactionType, transactionAmount}}/></td>
      {index === 0 && <td rowSpan={noOfRowsGrouped}>{totalCredit.toFixed(2)}</td>}
      {index === 0 && <td rowSpan={noOfRowsGrouped}>{totalDebit.toFixed(2)}</td>}
      {index === 0 && <td rowSpan={noOfRowsGrouped}>{balance.toFixed(2)}</td>}
      {index === 0 && <td rowSpan={noOfRowsGrouped}>{cumulativeBalance.toFixed(2)}</td>}
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