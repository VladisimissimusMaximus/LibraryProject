<%@ page contentType="text/html" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<div class="container position-fixed ml-3 mt-3">
    <p class="h3 text-center"><b>Library</b></p>
    <br/>
    <p>The reader registers in the system and then can:</p>
    <ul>
        <li>
            search (by author / title);
        </li>
        <li>
            place an order for a Book from the Catalog.
        </li>
    </ul>

    <p>For the catalog the ability to sort books has to be implemented: </p>
    <ul>
        <li>
            by name;
        </li>
        <li>
            by author;
        </li>
        <li>
            by publication;
        </li>
        <li>
            by date of publication.
        </li>
    </ul>

    <p>The librarian gives the reader a book on a subscription or to the reading room. The book is issued to the reader for a certain period. <br/>
        If the book is not returned within the specified period, the reader will be fined. <br/>
        The book may be present in the library in one or more copies. <br/>
        The system keeps track of the available number of books. Each user has a personal account that displays registration information <br/>
    </p>
    <p>And also: </p>
    <ol>
        <li>
            for reader:
            <ul>
                <li>
                    list of books on the subscription and date of possible return (if the date is overdue, the amount of the fine is displayed);
                </li>
            </ul>
        </li>
        <li>
            for librarian:
            <ul>
                <li>
                    list of readers' orders;
                </li>
                <li>
                    list of readers and their subscriptions.
                </li>
            </ul>
        </li>
    </ol>
    <p>
        The system administrator has the rights
    </p>
    <ul>
        <li>
            adding / deleting a book, editing information about the book;
        </li>
        <li>
            create / delete librarian;
        </li>
        <li>
            blocking / unblocking the user.
        </li>
    </ul>

</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
