/**
 * 
 */

 //SIDEBAR DROPDOWN
const allDropdown = document.querySelectorAll('#sidebar .side-dropdown');
const sidebar = document.querySelector('.sidebar');
for (let i = 0; i < allDropdown.length; i++) {
    const item = allDropdown[i];
    const a = item.parentElement.querySelector('a:first-child');
    a.onclick = function (e) {
        e.preventDefault();
        if (!this.classList.contains('active')) {
            for (let j = 0; j < allDropdown.length; j++) {
                const i = allDropdown[j];
                const aLink = i.parentElement.querySelector('a:first-child');
                aLink.classList.remove('active');
                i.classList.remove('show');
            }
        }
        this.classList.toggle('active');
        if(body.classList.contains('dark')){
            this.classList.toggle('dark');
        }
        item.classList.toggle('show');
    };
}

// SIDEBAR COLLAPSE
const toggleSidebar = document.querySelector('nav .toggle-sidebar');
const allSideDivider = document.querySelectorAll('#sidebar .divider');

const originalBgBackground = window.getComputedStyle(document.body).backgroundColor;
const windowSize = screen.width;

function adjustWidth() {
	return window.innerWidth <= 768 ? 'phone' : 'laptop';
}

const size = adjustWidth();

window.addEventListener('load', handleWidthChange);
window.addEventListener('resize', handleWidthChange);

function handleWidthChange() {
    const size = adjustWidth();
	//console.log(size);
	//console.log(size);
    if (size === 'phone') {
        //if (sidebar.classList.contains('hide')) {
			sidebar.classList.add('hide');
            allSideDivider.forEach(item => {
                item.textContent = '-';
            });
            allDropdown.forEach(item => {
                const a = item.parentElement.querySelector('a:first-child');
                // a.classList.remove('active');
                // item.classList.remove('show');
            });
        //}
    } else if (size === 'laptop') {
         if (!sidebar.classList.contains('hide')) {
			//sidebar.classList.remove('hide');
            allSideDivider.forEach(item => {
                item.textContent = item.dataset.text;
            });	
        }
    }    
    const title = document.getElementById('dkjfh');
    if(title) {
        console.log("dkfj");
        
    }
    console.log(size);
    if(size === 'phone' && sidebar.classList.contains('hide')) {
        console.log("laptophide");
        title.style.marginLeft = '500px';
    }
}



toggleSidebar.addEventListener('click', function () {
	sidebar.classList.toggle('hide');

	if(sidebar.classList.contains('hide')) {
		allSideDivider.forEach(item=> {
			item.textContent = '-'
		})

		allDropdown.forEach(item=> {
			const a = item.parentElement.querySelector('a:first-child');
			// a.classList.remove('active');
			// item.classList.remove('show');
		})
	} else {
		allSideDivider.forEach(item=> {
			item.textContent = item.dataset.text;
		})
	}
})


// Dropdown toggle function
function dropdownToggle(clickableElement, dropdownModal) {
    if(clickableElement && dropdownModal) {
        clickableElement.addEventListener('click', function() {
            dropdownModal.classList.toggle('show');
        });
    }
}

// MENU DROPDOWN
const menuIcon = document.querySelector('.menu-icon');
const menuLink = document.querySelector('.menu-link');
dropdownToggle(menuIcon, menuLink);

const menuIcon2 = document.querySelector('.menu-icon-2');
const menuLink2 = document.querySelector('.profile-menu-link');
dropdownToggle(menuIcon2, menuLink2);

// PROFILE DROPDOWN
const profile = document.querySelector('nav .profile');
const imgProfile = profile.querySelector('nav .profile img');
const dropdownProfile = profile.querySelector('.profile-link');
dropdownToggle(imgProfile, dropdownProfile);

// NOTIFICATION DROPDOWN
const notification = document.querySelector('.notification');
const notiIcon = document.querySelector('.nav-link.notifications .notification-icon-group');
const dropdownNotification = document.querySelector('.noti-link');
dropdownToggle(notiIcon, dropdownNotification);

// notiIcon.addEventListener('click', function (e) {
//     dropdownNotification.classList.toggle('show');
// });

// MENU DROPDOWN
const leaveMenu = document.querySelector('.leave-menu');
const leaveMenuIcon = document.querySelectorAll('.leave-menu .leave-menu-icon');
const dropdownLeaveMenu = document.querySelectorAll('.leave-menu-link');
tableDropdownToggle(leaveMenuIcon, dropdownLeaveMenu);

function tableDropdownToggle(clickableElements, dropdownModals) {
    if(clickableElements && dropdownModals && clickableElements.length === dropdownModals.length) {
        clickableElements.forEach((clickableElement, index) => {
            clickableElement.addEventListener('click', function() {
                dropdownModals[index].classList.toggle('show');
            });
        });
    }
}

// REMOVE MODAL
const removeModalGroup = document.querySelector('#remove-modal-group');
const removeBtn = document.getElementById('removeBtn');
const leaveAcceptRejectFormModal = document.querySelector('#leaveAcceptRejectFormModal');
const removeModal = document.querySelector('#remove-modal-group #remove-modal');
const removeModalCancelBtn = document.getElementById('cancelBtnRemove');
removeBtn.addEventListener('click', function (e) {
    removeModalGroup.classList.add('show');
});

removeModalCancelBtn.addEventListener('click', function (e) {
    removeModalGroup.classList.remove('show');
});

// Close dropdown if the clicked element is not within it
window.addEventListener('click', function (e) {    
    if (!imgProfile.contains(e.target) && !dropdownProfile.contains(e.target)) {
        dropdownProfile.classList.remove('show');
    }

    if (!notiIcon.contains(e.target) && !dropdownNotification.contains(e.target) && !removeModalGroup.contains(e.target)) {
        dropdownNotification.classList.remove('show');
    }
	if (!menuIcon.contains(e.target) && !menuLink.contains(e.target) && !removeModalGroup.contains(e.target)) {
        menuLink.classList.remove('show');
    }
    //if (!leaveMenuIcon.contains(e.target) && !dropdownLeaveMenu.contains(e.target) && !leaveAcceptRejectFormModal.contains(e.target)) {
      //  dropdownLeaveMenu.classList.remove('show');
    //}
    let clickedInsideDropdown = false;
    dropdownLeaveMenu.forEach((dropdown) => {
        if (dropdown.contains(e.target)) {
            clickedInsideDropdown = true;
        }
    });
    
    if (!leaveMenuIcon.contains(e.target) && !clickedInsideDropdown) {
        dropdownLeaveMenu.forEach((dropdown) => {
            dropdown.classList.remove('show');
        });
    }
});

// darkmode
const body = document.querySelector("body");
const sidebarItems = document.querySelector(".sidebar .side-menu li a.active");
const darkLightText = document.querySelector(".dark-light");
const userProfile = document.querySelector(".user-profile");
const aLink = document.querySelector(".sidebar .side-menu li a")

document.addEventListener("DOMContentLoaded", function() {
    // Check if dark mode is stored in localStorage
    const isDarkMode = localStorage.getItem("darkMode") === "true";
    console.log(isDarkMode);
    applyDarkMode(isDarkMode);
});
const nav = document.querySelector('.content nav');
const allCards = document.querySelectorAll('.card');

window.addEventListener('resize', function() {
    console.log("resize");
    allCards.forEach(card => {
        const cardWidth = parseInt(getComputedStyle(card).width);
        if (cardWidth > 280) {
            card.classList.add('space-between');
            console.log("a");
        } else {
            card.classList.remove('space-between');
        }
    });
});

window.addEventListener('load', function() {
    allCards.forEach(card => {
        const cardWidth = parseInt(getComputedStyle(card).width);
        if (cardWidth > 280) {
            card.classList.add('space-between');
            console.log("b");
        } else {
            card.classList.remove('space-between');
        }
    });
});

function applyDarkMode(isDarkMode) {
    body.classList.toggle("dark", isDarkMode);
	if(isDarkMode) {
		darkLightText.innerHTML = `<i class="bx bxs-moon icon" style="font-size: 28px; color: #ffce45;" onclick="darkmode()"></i><span class="tooltiptext">Change light mode</span>`;
	} else {
		darkLightText.innerHTML = `<i class="bx bx-moon icon" style="font-size: 28px;" onclick="darkmode()"></i><span class="tooltiptext">Change dark mode</span>`;
	}
	menuLink.classList.toggle("dark", isDarkMode);
	sidebarItems.classList.toggle("dark", isDarkMode);
	allCards.forEach(card => {
        card.classList.toggle("dark", isDarkMode); 
    });
    if(userProfile) {
        userProfile.classList.toggle("dark", isDarkMode);
    }
    aLink.classList.toggle("dark", isDarkMode);
    localStorage.setItem("darkMode", isDarkMode.toString());
}

function darkmode() {
    const isDarkMode = body.classList.toggle("dark");
    applyDarkMode(isDarkMode);
}

// make all as read
document.addEventListener('DOMContentLoaded', function () {
    const readClickSection = document.querySelector("#read");
    const unreadElements = document.querySelectorAll(".noti-text .unread");

    if (readClickSection && unreadElements.length > 0) {
        readClickSection.addEventListener('click', function (e) {
            unreadElements.forEach(unreadElement => {
                unreadElement.classList.add("hide");
            });
        });
    } else {
        console.error("Elements not found. Check your HTML or class names.");
    }
});

// COPY
function copyText() {
    const textToCopy = "https:/vinntrack/alvin151";
    navigator.clipboard.writeText(textToCopy).then(() => {
        console.log('Text copied to clipboard:', textToCopy);
    }).catch((error) => {
        console.error('Unable to copy text to clipboard', error);
    });
    document.getElementById("copied-tooltip").style.visibility = 'visible';
    setTimeout(() => {
        document.getElementById("copied-tooltip").style.visibility = 'hidden';
    }, 1000);
}

function toggleEditMode(rowId, inputSelector) {
    const row = document.getElementById(rowId);
    const editBtn = row.querySelector('.editBtn span');
    const userSpan = row.querySelector('.user-data span');
    const inputField = row.querySelector(`.user-data ${inputSelector}`);
    
    if (row.classList.contains('edit-mode')) {
        // Save the edited value
        userSpan.textContent = inputField.value;

        // Switch back to view mode
        row.classList.remove('edit-mode');
        editBtn.textContent = 'Edit';
    } else {
        // Switch to edit mode
        row.classList.add('edit-mode');
        editBtn.textContent = 'Save';

        // Populate the input field with the current value
        inputField.value = userSpan.textContent;
    }
}

// Profile Form Toggle
function toggleForm(className) {
    var formRow = document.getElementsByClassName(className)[0];
    var spanRowId = className.replace('form-row-', 'user-info-');
    console.log(spanRowId);
    var spanRow = document.getElementById(spanRowId);
    console.log("span");
    console.log(spanRow);
    const dataSpan = spanRow.querySelector('span.user-data-from-db');
    console.log(dataSpan);
    const inputField = formRow.querySelector('.edit-form .input-box input');
    console.log(inputField);
    const data = formRow.querySelector('span#output-data');
    if (formRow.classList.contains('open')) {
        formRow.classList.remove('open');
    } else {
        if(dataSpan) {
            console.log(dataSpan.textContent);
        }
        
        formRow.classList.add('open');
        if(inputField && dataSpan) {
            inputField.value = dataSpan.textContent;
        }
        if(data) {
            data.textContent = dataSpan.textContent;
        }
        
    }
}

function hide(className) {
    console.log(className);
    console.log("hide");
    var formRow = document.getElementsByClassName(className)[0];
    var spanRowId = className.replace('form-row-', 'user-info-');
    var spanRow = document.getElementById(spanRowId);
    console.log("span");
    console.log(spanRow);
    //console.log(className);
    //const row = element.closest('tr');
    if (spanRow) {
        console.log(spanRow);
        //console.log(row);
        const userDataElement = spanRow.querySelector('span.user-data-from-db');
        if (userDataElement) {
            //console.log(userDataElement);
            const originalText = userDataElement.textContent;
            const originalTextClassName = userDataElement.className;
            // console.log(originalText);
            // console.log(originalTextClassName);
            // Store the original text and class name as data attributes
            spanRow.setAttribute('data-original-text', originalText);
            spanRow.setAttribute('data-original-class', originalTextClassName);

            const asterisks = '*'.repeat(originalText.length);
            // const asterisks = ' * * * * *';
            // userDataElement.innerHTML = `<s>${originalText}</s>`;
            userDataElement.style.textDecoration = 'line-through';
            
            const hideBtn = formRow.querySelector('.hide-button');
            // Change the text of the "Show" button
            if (hideBtn) {
                hideBtn.textContent = 'Show Details';
                hideBtn.onclick = function () { show(`${className}`); };

            }
        }
    } else {
        console.log("not");
    }
}

function show(className) {
    var formRow = document.getElementsByClassName(className)[0];
    var spanRowId = className.replace('form-row-', 'user-info-');
    var spanRow = document.getElementById(spanRowId);
    if (spanRow) {
        console.log(spanRow);
        const userDataElement = spanRow.querySelector('.user-data-from-db');
        if (userDataElement) {
            console.log(userDataElement);
            // Retrieve the original text and class name from data attributes
            const originalText = spanRow.getAttribute('data-original-text');
            console.log(originalText);
            const originalTextClassName = spanRow.getAttribute('data-original-class');
            console.log(originalTextClassName);

            // Revert the hidden elements back to their original state
            userDataElement.textContent = originalText;
            userDataElement.style.textDecoration = 'none';
            const hideBtn = formRow.querySelector('.hide-button');
            // Change the text of the "Show" button back to "Hide"
            if (hideBtn) {
                hideBtn.textContent = 'Hide Details';
                hideBtn.onclick = function () { hide(className); };
            }
        } else {
            console.log("not element");
        }
    } else {
        console.log("not");
    }
}

document.addEventListener('DOMContentLoaded', function () {
    const monthSelect = document.getElementById('month');
    const monthFromDb = document.getElementById('monthFromDb');
    const dateFromDb = document.getElementById('dateFromDb');
    const yearFromDb = document.getElementById('yearFromDb');
    //const genderFromDb = document.getElementById('genderFromDb');
    document.getElementById('formDate').value = dateFromDb.innerText;
    document.getElementById('formYear').value = yearFromDb.innerText;
    const genderFromDb = document.getElementById('genderFromDb');
console.log('genderFromDb:', genderFromDb.textContent.trim().toLowerCase());
const formGenderRadios = document.querySelectorAll('.formGender');
console.log("formGenderRadios:", formGenderRadios);

// Set the value of the radio button based on the span content
formGenderRadios.forEach(radio => {
    console.log('radio.value:', radio.value.toLowerCase());
    if (radio.value.toLowerCase() === genderFromDb.textContent.trim().toLowerCase()) {
        console.log("Match! Setting to checked.");
        radio.setAttribute('aria-checked', 'true');
        radio.checked = true; // Check the radio button visually
    } else {
        console.log("No match.");
        radio.checked = false; // Check the radio button visually
    }
});




    //Function to display months
    function displayMonths() {
        const months = [
            'January', 'February', 'March', 'April', 'May', 'June',
            'July', 'August', 'September', 'October', 'November', 'December'
        ];

        // Clear previous options
        monthSelect.innerHTML = '';

        // Generate new month options
        for (let i = 0; i < months.length; i++) {
            const option = document.createElement('option');
            option.value = months[i];
            option.textContent = months[i];
            monthSelect.appendChild(option);
        }
    }

    // Function to set selected value based on span content
    function setSelectValue() {
        const spanContentMonth = monthFromDb.textContent.trim();

        // Loop through options to find a match
        for (let i = 0; i < monthSelect.options.length; i++) {
            if (monthSelect.options[i].value === spanContentMonth) {
                monthSelect.selectedIndex = i;
                break;
            }
        }
    }

    // FUNCTION GENDER RADIO BUTTON
    

    // Initial display function
    displayMonths();
    // Initial set
    setSelectValue();
    
});


// BOX SHADOW NAVBAR
const navbar = document.querySelector('nav');
    console.log(navbar);
    const content = document.querySelector('.content');
    console.log(content);

function scrollNav() {
    
    
    if (navbar) {
        console.log("scrolling");
        if (content.scrollTop  > 0) {
            
            navbar.classList.add('scrolled');
        } else {
            navbar.classList.remove('scrolled');
        }
    }
}

// Attach the scrollNav function to the scroll event
content.addEventListener('scroll', scrollNav);

// Attendance percent
const totalEmployee =  document.getElementById('totalEmployee');
const totalAttendanceEmployee =  document.getElementById('totalAttendanceEmployee');
const attendancePercent = document.getElementById('attendancePercent');
const leavePercent = document.getElementById('leavePercent');
function setAttendanePercent() {
    const totalEmployeeFloatValue = parseFloat(totalEmployee.innerText);
    console.log("total employee",totalEmployeeFloatValue);
    const totalAttendanceEmployeeFloatValue = parseFloat(totalAttendanceEmployee.innerText);
    console.log("today attendance",totalAttendanceEmployeeFloatValue);

    const attendancePercentValue = (totalAttendanceEmployeeFloatValue * 100) / totalEmployeeFloatValue;
    console.log(attendancePercentValue);
    attendancePercent.textContent = `${attendancePercentValue}%`;
    const leavePercentValue = 100 - attendancePercentValue;
    leavePercent.textContent = `${leavePercentValue}%`;
}

if(totalEmployee) {
    setAttendanePercent();
}