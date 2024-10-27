'use strict'
console.log("Contact.js");

const ViewContactModal = document.getElementById('view_contact_modal');

const baseUrl= 'http://localhost:8081';

const options = {
    placement: 'bottom-right',
    backdrop: 'dynamic',
    backdropClasses: 'bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40',
    closable: true,
    onHide: () => {
        console.log('modal is hidden');
    },
    onShow: () => {
        console.log('modal is shown');
    },
    onToggle: () => {
        console.log('modal has been toggled');
    },
};

const instanceOptions = {
    id: 'view_contact_modal',
    override: true
};

const constModal = new Modal(ViewContactModal, options, instanceOptions);

function OpenContactModal() {
    constModal.show();
}

function CloseContactModal() {
    constModal.hide();
}

async function loadUserData(id){
    console.log(id);
    
    try {
        const data = await (await fetch(`${baseUrl}/api/contact/${id}`)).json();
    console.log(data);
    document.getElementById('contact_img').src=data.picture;
    document.getElementById('contact_name').innerHTML=data.name;
    document.getElementById('description').innerHTML=data.description;
    document.getElementById('email').innerHTML=data.email;
    document.getElementById('phoneNumber').innerHTML=data.phoneNumber;
    document.getElementById('address').innerHTML=data.address;
    document.getElementById('websteLink').href=data.websteLink;  
    document.getElementById('linkedinLink').href=data.linkedinLink;

    console.log(data.linkedinLink);

    OpenContactModal();

    } catch (error) {
        console.log(error);
    }
}



// Delete Contact 

async function deleteContact(id){

    console.log(id);
    Swal.fire({
        title: "Do you want to Delete the Contact?",
        icon:"warning",
        // showDenyButton: true,
        showCancelButton: true,
        confirmButtonText: "Delete",
        // denyButtonText: `Don't save`
      }).then((result) => {
        /* Read more about isConfirmed, isDenied below */
        if (result.isConfirmed) {
            const url = `${baseUrl}/user/contacts/delete/` + id;
            window.location.replace(url);
            //  Swal.fire("Contact  Deleted!", "", "success");
        } 
      });
}