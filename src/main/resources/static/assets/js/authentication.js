/**
 * 
 */
/**
 * 
 */
// loginModal.js
// Functions for opening and closing terms and conditions modal
const terms_modal = document.getElementById('terms-and-condition-modal');
function openTermsModal() {
    terms_modal.style.display = 'block';
}

function closeTermsModal() {
    terms_modal.style.display = 'none';
}

// Functions for opening and closing privacy policy modal
const policy_modal = document.getElementById('privacy-policy-modal');
function openPolicyModal() {
    policy_modal.style.display = 'block';
}

function closePolicyModal() {
    policy_modal.style.display = 'none';
}

//otpVerificationScript.js
    const inputs = document.querySelectorAll(".input-field input");
    //button = document.getElementById("otpBtn");
    const button = document.querySelector(".otpBtn");

    // iterate over all inputs
    inputs.forEach((input, index1) => {
        input.addEventListener("keyup", (e) => {
            // This code gets the current input element and stores it in the currentInput variable
            // This code gets the next sibling element of the current input element and stores it in the nextInput variable
            // This code gets the previous sibling element of the current input element and stores it in the prevInput variable
            const currentInput = input,
            nextInput = input.nextElementSibling,
            prevInput = input.previousElementSibling;

            // if the value has more than one character then clear it
            if (currentInput.value.length > 1) {
                currentInput.value = "";
                return;
            }
            // if the next input is disabled and the current value is not empty
            //  enable the next input and focus on it
            if (nextInput && nextInput.hasAttribute("disabled") && currentInput.value !== "") {
                nextInput.removeAttribute("disabled");
                nextInput.focus();
            }

            // if the backspace key is pressed
            if (e.key === "Backspace") {
            // iterate over all inputs again
                inputs.forEach((input, index2) => {
                    // if the index1 of the current input is less than or equal to the index2 of the input in the outer loop
                    // and the previous element exists, set the disabled attribute on the input and focus on the previous element
                    if (index1 <= index2 && prevInput) {
                    input.setAttribute("disabled", true);
                    input.value = "";
                    prevInput.focus();
                    }
                });
            }
            //if the fourth input( which index number is 3) is not empty and has not disable attribute then
            //add active class if not then remove the active class.
            if (!inputs[3].disabled && inputs[3].value !== "") {
                button.classList.add("active");
                if(button.classList.contains("active")){
                    button.addEventListener("click", function() {
                        toggleSections('createNewPasswordSection');
                    });
                }
                return;
            }        
            button.classList.remove("active");
        });
    });

    //focus the first input which index is 0 on window load
    window.addEventListener("load", () => inputs[0].focus());