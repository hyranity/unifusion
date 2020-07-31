<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>UniFusion :: Dashboard</title>
        <link rel="stylesheet" href="CSS/dashboard.css">
    </head>
    <body>
  <div id='container'>

    <a href='#' id='back'>&lt; Back</a>

    <div id='top'>
      <a id='heading'>Dashboard</a>
      <div id='actions'>
        <a href='#'>Join</a>
        <a href='#'>Create</a>
      </div>
    </div>

    <div id='classes'>
    
    <!-- course -->
      
      <div class='course'>
      
        <div class='course-details'>
          <img class='icon' src='https://image.flaticon.com/icons/svg/3034/3034573.svg'>
          <div class='details'>
            <div class='top-details'>
              <a class='id'>CO001</a>
              <a class='tutor'>John Doe</a>
            </div>
            <a class='type'>COURSE</a>
            <a class='name'>Computer Science</a>
            <a class='description'>Bachelor-level course</a>
          </div>
        </div>
        
        <input type='checkbox' name='seeClasses' class='seeClasses' id='seeClasses_CO001' onclick='seeClasses("CO001")'>
        <label class='seeClassesLabel' id='seeClassesLabel_CO001' for='seeClasses_CO001'>View classes</label>
        
        <div class='classes' id='classes_CO001'>
      
          <div class='row'>

            <div class='class' onclick="location.href='#';">
              <img class='icon' src='https://image.flaticon.com/icons/svg/3034/3034573.svg'>
              <div class='details'>
                <div class='top-details'>
                  <a class='id'>C001</a>
                  <a class='tutor'>John Doe</a>
                </div>
                <a class='type'>CLASS</a>
                <a class='name'>Math</a>
                <a class='description'>Intermediate</a>
              </div>
            </div>

            <div class='class' onclick="location.href='#';">
              <img class='icon' src='https://image.flaticon.com/icons/svg/3034/3034573.svg'>
              <div class='details'>
                <div class='top-details'>
                  <a class='id'>C001</a>
                  <a class='tutor'>John Doe</a>
                </div>
                <a class='type'>CLASS</a>
                <a class='name'>Math</a>
                <a class='description'>Intermediate</a>
              </div>
            </div>

          </div>
        
        </div>
        
      </div>
      
      <!-- end of course -->
    
      <!-- row 1 -->

      <div class='row'>
      
        <!-- row 1, class 1 -->

        <div class='class' id='orange' onclick="location.href='#';">
          <img class='icon' src='https://image.flaticon.com/icons/svg/3034/3034573.svg'>
          <div class='details'>
            <div class='top-details'>
              <a class='id'>C001</a>
              <a class='tutor'>John Doe</a>
            </div>
            <a class='name'>Math</a>
            <a class='description'>Intermediate arithmetic</a>
          </div>
        </div>
        
        <!-- row 1, class 2 -->

        <div class='class' onclick="location.href='#';">
          <img class='icon' src='https://image.flaticon.com/icons/svg/3034/3034573.svg'>
          <div class='details'>
            <div class='top-details'>
              <a class='id'>C001</a>
              <a class='tutor'>John Doe</a>
            </div>
            <a class='name'>Math</a>
            <a class='description'>Intermediate</a>
          </div>
        </div>

      </div>
      
      <!-- row 2 -->
      
      <div class='row'>
      
        <!-- row 2, class 1 -->

        <div class='class' onclick="location.href='#';">
          <img class='icon' src='https://image.flaticon.com/icons/svg/3034/3034573.svg'>
          <div class='details'>
            <div class='top-details'>
              <a class='id'>C001</a>
              <a class='tutor'>John Doe</a>
            </div>
            <a class='name'>Math</a>
            <a class='description'>Intermediate</a>
          </div>
        </div>
        
        <!-- row 2, class 2 -->

        <div class='class' id='green' onclick="location.href='#';">
          <img class='icon' src='https://image.flaticon.com/icons/svg/3034/3034573.svg'>
          <div class='details'>
            <div class='top-details'>
              <a class='id'>C001</a>
              <a class='tutor'>John Doe</a>
            </div>
            <a class='name'>Math</a>
            <a class='description'>Intermediate arithmetic</a>
          </div>
        </div>

      </div>

    </div>

  </div>
  
<script>
function seeClasses(courseId) {
	var checkbox = document.getElementById("seeClasses_" + courseId);
  var classes = document.getElementById("classes_" + courseId);
  //alert(checkbox.checked);
  
  if (checkbox.checked == true){
    classes.style.display = "flex";
  } else {
    classes.style.display = "none";
  }
}
</script>
  
</body>
</html>
