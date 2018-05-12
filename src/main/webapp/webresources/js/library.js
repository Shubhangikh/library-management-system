$(document).ready(function(e) {
    $('#first-name').focus();

    $("a").click(function(e) {
        e.preventDefault();
        var divId = $(this).attr("href");
        if(divId === '#register-block') {
            $( '#clear-data' ).trigger( 'click' );
        } else if(divId === '#search-block') {
            $( '#book-list' ).empty();
        } else if(divId === '#check-in-block') {
            $('#checkin-book-list' ).empty();
            $('#check-in-text').val('');
        } else if(divId === '#fine-payment-block') {
            $("#fine-list" ).empty();
            $('#due-amount-block' ).hide();
            $('#total-fine').val('');
        } else {

        }
        $(divId).show().siblings().hide();
    });

    $('#clear-data').click(function () {
        $('#register-block').find(':input').val('');
    });

    $(document).on("click", ".btn-checkout", function(){
        $('#borrower-data-modal').modal('show');
        $('#card-id-checkout').val('');
        $('#btn-final-checkout').attr('data-isbn10', $(this).attr('data-isbn'));
    });

    $(document).on("click", ".btn-check-in", function(){
        var requestForm = {};
        requestForm.loanId = $(this).attr('id');
        $.ajax({
            url : 'bookcheckin.action',
            type : 'POST',
            data : JSON.stringify(requestForm),
            processData : false,
            contentType : 'application/json',
            success : function(data) {
                if(data.status === 1) {
                    $('#success-message-block').text(data.message).slideDown("slow");
                    setTimeout(function() {
                        $('#success-message-block').slideUp("slow");
                    }, 3000);
                } else
                {
                    $('#error-message-block').text(data.message).slideDown("slow");
                    setTimeout(function() {
                        $('#error-message-block').slideUp("slow");
                    }, 3000);
                }
            },
            error : $('#error-message-block').text('Looks like server is taking too long to respond. Please try again later.')
        });
    });

    $(document).on("click", ".btn-pay-fine", function(){
        var requestForm = {};
        requestForm.loanId = $(this).attr('id');
        $.ajax({
            url : 'payfine.action',
            type : 'POST',
            data : JSON.stringify(requestForm),
            processData : false,
            contentType : 'application/json',
            success : function(data) {
                if(data.status === 1) {
                    $('#success-message-block').text(data.message).slideDown("slow");
                    setTimeout(function() {
                        $('#success-message-block').slideUp("slow");
                    }, 3000);
                } else
                {
                    $('#error-message-block').text(data.message).slideDown("slow");
                    setTimeout(function() {
                        $('#error-message-block').slideUp("slow");
                    }, 3000);
                }
            },
            error : $('#error-message-block').text('Looks like server is taking too long to respond. Please try again later.')
        });
    });

    $('#btn-search-borrower').click(function () {
        var requestForm = {};
        requestForm.searchString = $('#borrower-search-text').val();
        $.ajax({
            url : 'borrowerfineamounts.action',
            type : 'POST',
            data : JSON.stringify(requestForm),
            processData : false,
            contentType : 'application/json',
            success : function(data) {
                $("#fine-list" ).empty();
                if(data.status === 0) {
                    $('#error-message-block').text(data.message).slideDown("slow");
                    $('#due-amount-block' ).hide();
                    $('#total-fine').val('');
                    setTimeout(function() {
                        $('#error-message-block').slideUp("slow");
                    }, 3000);
                } else {
                    $('#due-amount-block' ).show();
                    $('#total-fine').val(data.totalFineAmount);
                }
                var fineList = $('ul#fine-list');
                $.each( data.fineDetails, function( index ) {
                    var fineDetails = $('<li/>').addClass('list-group-item');
                    var cardId = $('<span />').html('Card Id : ' + this.cardId);
                    var name = $('<span />').html("Borrower's Name : " + this.bName);
                    var isbn = $('<span />').html('ISBN : ' + this.isbn);
                    var fine = $('<span />').html('Fine Amount : $' + this.fineAmount);
                    var payFineIcon = $('<span />').addClass('glyphicon glyphicon-hand-right');
                    var payFine = $('<button />').addClass('btn btn btn-warning btn-pay-fine').html(payFineIcon);
                    $(payFine).prop('title','Pay');
                    $(payFine).prop('type','Submit');
                    $(payFine).attr('id', this.loanId);

                    fineDetails.append([cardId, name, isbn, fine, payFine]);
                    $(fineList).append(fineDetails);
                });
            },
            error : $('#error-message-block').text('Looks like server is taking too long to respond. Please try again later.')
        });
    });

    $('#update-fine-amounts').click(function () {
        $.ajax({
            url : 'updatefineamounts.action',
            type : 'POST',
            data : null,
            processData : false,
            contentType : 'application/json',
            success : function(data) {
                if(data.status === 1) {
                    $('#success-message-block').text(data.message).slideDown("slow");
                    setTimeout(function() {
                        $('#success-message-block').slideUp("slow");
                    }, 3000);
                }
            },
            error : $('#error-message-block').text('Looks like server is taking too long to respond. Please try again later.')
        });
    });

    $('#btn-final-checkout').click(function () {
        var requestForm = {};
        requestForm.cardId = $('#card-id-checkout').val();
        requestForm.isbn = $(this).attr('data-isbn10');
        $.ajax({
            url : 'bookcheckout.action',
            type : 'POST',
            data : JSON.stringify(requestForm),
            processData : false,
            contentType : 'application/json',
            success : function(data) {
                if(data.status === 1) {
                    $('#success-message-block').text(data.message).slideDown("slow");
                    setTimeout(function() {
                        $('#success-message-block').slideUp("slow");
                    }, 3000);
                } else
                {
                    $('#error-message-block').text(data.message).slideDown("slow");
                    setTimeout(function() {
                        $('#error-message-block').slideUp("slow");
                    }, 3000);
                }
            },
            error : $('#error-message-block').text('Looks like server is taking too long to respond. Please try again later.')
        });
    });

    $('#create-account').click(function () {
        var requestForm = {};
        var firstName = $('#first-name').val().trim();
        var lastName = $('#last-name').val().trim();
        var ssn = $('#ssn').val().trim();
        var address = $('#address').val().trim();
        var city = $('#city').val().trim();
        var state = $('#state').val().trim();
        if(firstName === '' || lastName === '' || ssn === '' || address === '' || city === '' || state === '') {
            $('#error-message-block').text('Name, SSN and Address are required for creating an account.').slideDown("slow");
            setTimeout(function() {
                $('#error-message-block').slideUp("slow");
            }, 3000);

            return;
        }
        requestForm.bName = firstName + ' ' + lastName;
        requestForm.ssn = ssn;
        requestForm.phone = '(' + $('#area-code').val() + ') ' + $('#co-code').val() + '-' + $('#line-num').val();
        requestForm.address = address + ',' + city + ',' + state;

        $.ajax({
            url : 'register.action',
            type : 'POST',
            data : JSON.stringify(requestForm),
            processData : false,
            contentType : 'application/json',
            success : function(data) {
                if(data.status === 1) {
                    $('#success-message-block').text(data.message).slideDown("slow");
                    setTimeout(function() {
                        $('#success-message-block').slideUp("slow");
                    }, 3000);
                } else
                {
                    $('#error-message-block').text(data.message).slideDown("slow");
                    setTimeout(function() {
                        $('#error-message-block').slideUp("slow");
                    }, 3000);
                }
            },
            error : $('#error-message-block').text('Looks like server is taking too long to respond. Please try again later.')
        });
    });

    $('#btn-search').click(function () {
        var requestForm = {};
        requestForm.searchString = $('#search-text').val();
        $.ajax({
            url : 'searchbooks.action',
            type : 'POST',
            data : JSON.stringify(requestForm),
            processData : false,
            contentType : 'application/json',
            success : function(data) {
                if(data.status === 0) {
                    $('#error-message-block').text(data.message).slideDown("slow");
                    setTimeout(function() {
                        $('#error-message-block').slideUp("slow");
                    }, 3000);
                }
                $("#book-list" ).empty();
                var bookList = $('ul#book-list');
                $.each( data.books, function( key, value ) {
                    var book = $('<li/>').addClass('list-group-item');
                    var isbn = $('<span />').html('ISBN : ' + value.isbn10);
                    var title = $('<span />').html('Title : ' + value.title);
                    var authors = $('<span />').html('Author(s) : ' + value.authors);
                    var isAvailable = $('<span />').html(value.isAvailable==='Yes'?'Available':'Unavailable');
                    var checkoutIcon = $('<span />').addClass('glyphicon glyphicon-shopping-cart');
                    var checkout = $('<button />').addClass('btn btn btn-warning btn-checkout').html(checkoutIcon);
                    $(checkout).prop('title','Checkout');
                    if(value.isAvailable === 'No') {
                        $(checkout).prop('disabled',true);
                    }
                    $(checkout).attr('data-isbn',value.isbn10);

                    book.append([isbn, title, authors, isAvailable, checkout]);
                    $(bookList).append(book);
                });


            },
            error : $('#error-message-block').text('Looks like server is taking too long to respond. Please try again later.')
        });
    });

    $('#btn-search-book').click(function () {
        var requestForm = {};
        requestForm.searchString = $('#check-in-text').val();
        $.ajax({
            url : 'checkinbookslist.action',
            type : 'POST',
            data : JSON.stringify(requestForm),
            processData : false,
            contentType : 'application/json',
            success : function(data) {
                if(data.status === 0) {
                    $('#error-message-block').text(data.message).slideDown("slow");
                    setTimeout(function() {
                        $('#error-message-block').slideUp("slow");
                    }, 3000);
                }
                $("#checkin-book-list" ).empty();
                var checkInBookList = $('ul#checkin-book-list');
                $.each( data.checkInBooks, function( index ) {
                    var date = new Date(this.dueDate);
                    var dateString = (date.getMonth() + 1) + "/" + date.getDate() + "/" + date.getFullYear();
                    var book = $('<li/>').addClass('list-group-item');
                    var isbn = $('<span />').html('ISBN : ' + this.isbn);
                    var cardId = $('<span />').html('Card Id : ' + this.cardId);
                    var name = $('<span />').html("Borrower's Name : " + this.bName);
                    var dueDate = $('<span />').html("Due Date : " + dateString);
                    var checkInIcon = $('<span />').addClass('glyphicon glyphicon glyphicon-check');
                    var checkIn = $('<button />').addClass('btn btn btn-warning btn-check-in').html(checkInIcon);
                    $(checkIn).prop('title','Check-In');
                    $(checkIn).prop('type','Submit');
                    $(checkIn).attr('id', this.loanId);

                    book.append([isbn, cardId, name, dueDate, checkIn]);
                    $(checkInBookList).append(book);
                });
            },
            error : $('#error-message-block').text('Looks like server is taking too long to respond. Please try again later.')
        });
    });
});