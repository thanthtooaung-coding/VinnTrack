// addScript.js

$(document).ready(function() {
    // Function to enable/disable the Add button based on input
    function toggleAddButton() {
        var departmentName = $('#deptName').val();
        var departmentAddress = $('#deptAddress').val();
        var positionName = $('#positName').val();
        var positionDepartment = $('#positDepartment').val();
        
        var departmentName = $('#deptName').val();        
        
        var addButton = $('.form-btn .form-button.save');

        // Check if values are undefined or null and handle them gracefully
        departmentName = departmentName ? departmentName.trim() : '';
        departmentAddress = departmentAddress ? departmentAddress.trim() : '';
        positionName = positionName ? positionName.trim() : '';
        positionDepartment = positionDepartment ? positionDepartment.trim() : '';

        if (departmentName !== '' && departmentAddress !== '') {
            // If the department name is not null, enable the button
            addButton.prop('disabled', false).addClass('active');
            console.log('Button enabled for department');
        } else if (positionName !== '' && positionDepartment !== '') {
            addButton.prop('disabled', false).addClass('active');
            console.log('Button enabled for position');
        } else {
            // If the department name is null, disable the button
            addButton.prop('disabled', true).removeClass('active');
            console.log('Button disabled');
        }
    }

    // Call the function on page load
    toggleAddButton();

    // Bind the function to the input field's keyup event
    $('#deptName, #deptAddress, #positName, #positDepartment').on('keyup change', function() {
        toggleAddButton();
    });

    // Additional logic from other scripts can be added here
    // ...
});
