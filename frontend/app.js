document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('registrationForm');
    const messageBox = document.getElementById('messageBox');
    const submitBtn = document.getElementById('submitBtn');

    form.addEventListener('submit', async (e) => {
        e.preventDefault(); 
        
       
        const name = document.getElementById('name').value.trim();
        const phone = document.getElementById('phone').value.trim();
        const email = document.getElementById('email').value.trim();
        const password = document.getElementById('password').value.trim();
        
       
        if (!name || !phone || !email || !password) {
            showMessage('Please fill in all fields.', 'error');
            return;
        }
        
      
        submitBtn.disabled = true;
        submitBtn.textContent = 'Registering...';
        
        try {
            const response = await fetch('http://localhost:8080/api/register', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    name: name,
                    phone: phone,
                    email: email,
                    password: password
                })
            });
            
            const data = await response.json();
            
            if (response.ok) {
                showMessage(data.message || 'Registration successful!', 'success');
                form.reset(); 
            } else {
                showMessage(data.error || 'Registration failed.', 'error');
            }
            
        } catch (error) {
            console.error('Network Error:', error);
            showMessage('Could not connect to the Java server.', 'error');
        } finally {
            submitBtn.disabled = false;
            submitBtn.textContent = 'Register';
        }
    });

    function showMessage(msg, type) {
        messageBox.textContent = msg;
        messageBox.className = `message ${type}`;
    }
});
