<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Simple GSP page</title>
    <script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
</head>
<meta name="layout" content="main"/>

<body>
<button id="doit">do it</button>
<script type="text/javascript">
    $('#doit').click(function () {
        var data = {
                        email: 'soeren@glasius.dk',
                        code: '9206',
                        favorites: [{uri: '1'},{uri: '7'},{uri: '2'}],
                        ratings: [{uri: '1', rating: 5, comment: 'kryf øf'},{uri: '4', rating: 5, comment: 'plyf'}]
                    }
        $.ajax({
            url: '${createLink(mapping: 'data', params: [space: 'eu2013'])}',
            method: 'POST',
            dataType: 'json',
            data: data, //{json: JSON.stringify(data)}
            success: function(data) {
                console.debug("Response: ",data)
            }
        })
    })
</script>
</body>
</html>
