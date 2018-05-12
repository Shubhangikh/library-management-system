<%--
  Created by IntelliJ IDEA.
  User: shude
  Date: 3/6/2018
  Time: 9:39 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>McDan Library</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="shortcut icon" type="image/x-icon" href="webresources/images/books.png" />
    <link rel="stylesheet" type="text/css" href="http://fonts.googleapis.com/css?family=Tangerine">
    <link rel="stylesheet" href="webresources/stylesheets/thirdparty/bootstrap.css">
    <link rel="stylesheet" href="webresources/stylesheets/thirdparty/bootstrap-theme.css">
    <link rel="stylesheet" href="webresources/stylesheets/library.css">
</head>
<body>
<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <a id="library-name" class="navbar-brand" href="#">McDan Library</a>
        </div>
        <ul class="nav navbar-nav">
            <li class="active"><a href="#search-block">Home</a></li>
            <li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="javascript:void(0)">Borrower Management<span class="caret"></span></a>
                <ul class="dropdown-menu">
                    <li><a href="#register-block">Register</a></li>
                    <li><a href="#check-in-block">Book Check-In</a></li>
                    <li><a href="#fine-payment-block">Fine Payment</a></li>
                </ul>
            </li>
        </ul>
    </div>
</nav>

<div  id="actions" class="container">
    <div id="search-block" class="container">
        <div class="search-block-heading row">
            <div class="col-lg-12">
                <h3><img src="webresources/images/books.png" alt="McDan Library" height="40" width="40" class="book-image">Welcome!</h3>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-12">
                <input type="text" class="form-control" id="search-text"
                       placeholder="Search for books:ISBN or Book Title or Author's Name" />
                <button type="submit" class="btn btn-primary" id="btn-search">
                    <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                </button>
            </div>
        </div>
        <div id="books-block" class="row">
            <ul id="book-list" class="list-group">

            </ul>
        </div>
    </div>
    <div hidden id="register-block" class="form-group container">
        <div class="search-block-heading row">
            <h3><img src="webresources/images/books.png" alt="McDan Library" height="40" width="40" class="book-image">Register New Account</h3>
            <h6 class="required-info">Fields marked with <span class="field-required">*</span> are required</h6>
        </div>
        <hr>
        <div class="row">
            <div class="col-lg-2">
                <label for="first-name">First Name</label><span class="field-required">*</span>
                <input type="text" class="form-control" id="first-name" required/>
            </div>
            <div class="col-lg-2">
                <label for="last-name">Last Name</label><span class="field-required">*</span>
                <input type="text" class="form-control" id="last-name" required/>
            </div>
        </div>
        <div class="div-gap"></div>
        <div class="row">
            <div class="col-lg-2">
                <label for="ssn">Social Security Number</label><span class="field-required">*</span>
                <input type="text" class="form-control" id="ssn" required/>
            </div>
            <div class="col-lg-4 container">
                <label>Mobile Number</label>
                <div id="phone-number-block" class="row">
                    <label>(&nbsp;</label><input type="text" class="form-control" id="area-code" /><label>&nbsp;)</label>&nbsp;
                    <input type="text" class="form-control" id="co-code" />&nbsp;<label>-</label>
                    <input type="text" class="form-control" id="line-num" />
                </div>
            </div>
        </div>
        <div class="div-gap"></div>
        <div class="row">
            <div class="col-lg-5">
                <label for="address">Address</label><span class="field-required">*</span>
                <input type="text" class="form-control" id="address"
                       required/>
            </div>
            <div class="col-lg-2">
                <label for="city">City</label><span class="field-required">*</span>
                <input type="text" class="form-control" id="city" required/>
            </div>
            <div class="col-lg-1">
                <label for="state">State</label><span class="field-required">*</span>
                <input type="text" class="form-control" id="state" required/>
            </div>
        </div>
        <div class="div-gap"></div>
        <div class="register-btn-block row">
            <button type="submit" class="btn btn-success" id="create-account">Create Account</button>
            <button type="reset" class="btn btn-primary" id="clear-data">Clear</button>
        </div>
        <hr>
    </div>
    <div hidden id="check-in-block" class="container">
        <div class="search-block-heading row">
            <div class="col-lg-12">
                <h3><img src="webresources/images/books.png" alt="McDan Library" height="40" width="40" class="book-image">Check-In a Book</h3>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-12">
                <input type="text" class="form-control" id="check-in-text"
                       placeholder="Search by ISBN or Card No. or Borrower's Name" />
                <button type="submit" class="btn btn-primary" id="btn-search-book">
                    <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                </button>
            </div>
        </div>
        <div id="checkin-books-block" class="row">
            <ul id="checkin-book-list" class="list-group">

            </ul>
        </div>
    </div>
    <div hidden id="fine-payment-block" class="container">
        <div class="search-block-heading row">
            <div class="col-lg-12">
                <h3><img src="webresources/images/books.png" alt="McDan Library" height="40" width="40" class="book-image">Fine Payment</h3>
            </div>
        </div>
        <div class="search-block-heading row">
            <div id="update-fine-block" class="col-lg-12">
                <button type="submit" id="update-fine-amounts" class="btn btn-success">Update Fine Amounts</button>
            </div>
        </div>
        <div class="div-gap"></div>
        <div class="row">
            <div class="col-lg-12">
                <input type="text" class="form-control" id="borrower-search-text"
                       placeholder="Search by Card No. or Borrower's Name" />
                <button type="submit" class="btn btn-primary" id="btn-search-borrower">
                    <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                </button>
            </div>
        </div>
        <div class="div-gap"></div>
        <div class="row">
            <div hidden class="col-lg-2" id="due-amount-block">
                <label for="total-fine">Total Amount Due</label>
                <label>($)</label><input type="text" disabled class="form-control" id="total-fine"/>
            </div>
        </div>
        <div id="fine-block" class="row">
            <ul id="fine-list" class="list-group">

            </ul>
        </div>
    </div>
</div>
<div class="modal fade" id="borrower-data-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Book Check-out</h5>

            </div>
            <div class="modal-body">
                <label for="card-id-checkout">Card Id</label>
                <input type="text" class="form-control" id="card-id-checkout" required/>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                <button type="button" id="btn-final-checkout" class="btn btn-primary">Checkout</button>
            </div>
        </div>
    </div>
</div>
<div hidden class="alert alert-success" id="success-message-block" role="alert">
    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
        <span aria-hidden="true">&times;</span>
    </button>
</div>
<div hidden class="alert alert-warning" id="error-message-block" role="alert">
    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
        <span aria-hidden="true">&times;</span>
    </button>
</div>
<script src="webresources/js/thirdparty/jquery-3.3.1.js"></script>
<script src="webresources/js/thirdparty/bootstrap.js"></script>
<script src="webresources/js/library.js"></script>
</body>
</html>
