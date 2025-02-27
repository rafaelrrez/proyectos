@vite(['resources/js/app.js'])

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" href="../public/css/login-signup/style.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">
    <link rel="icon" href="{{ asset('/img/favicon.png') }}" type="image/x-icon">
    <title>{{ __('login') }} | Tecno Market</title>

</head>

<body>

    <!-- blue circle background -->
    <div class="d-none d-md-block ball login bg-primary bg-gradient position-absolute rounded-circle"></div>

    <!-- logo image -->
    <div class="position-absolute top-0 start-0 p-3">
        <a href="{{ route('index') }}" class="text-decoration-none">
            <img src="{{ asset('/img/logo.png') }}" alt="Tecno Market Logo" class="img-fluid" style="max-width: 100px;">
        </a>
    </div>

    <header class="header">
            <div class="container px-lg-3">
                <nav class="navbar navbar-expand-lg navbar-light py-3 px-lg-0"><a class="navbar-brand"
                        href="{{ route('index') }}">
                    <div class="collapse navbar-collapse" id="navbarSupportedContent">
                       
                        <ul class="navbar-nav ms-auto">
                            <li class="nav-item">
                                <a href="{{ route('set_language', ['language' => 'en']) }}" class="nav-link">
                                    <img src="{{ asset('img/icon_uk.png') }}" alt="Bandera de España" class="me-1 user-icon" style="max-width: 20px;">
                                </a>
                            </li>
                            <li class="nav-item">
                                <a href="{{ route('set_language', ['language' => 'es']) }}" class="nav-link">
                                    <img src="{{ asset('img/icon_espana.png') }}" alt="Bandera de España" class="me-1 user-icon" style="max-width: 20px;">
                                </a>
                            </li>
                        </ul>
                    </div>
                </nav>
            </div>
        </header>

    <!-- Login Section -->
    <div class="container login__form active">
        <div class="row vh-100 w-100 align-self-center">
            <div class="col-12 col-lg-6 col-xl-6 px-5">
                <div class="row vh-100">
                    <div class="col align-self-center p-5 w-100">
                        <h3 class="fw-bolder">{{ __('welcome_back') }}</h3>
                        <p class="fw-lighter fs-6">{{ __('dont_have_account') }}<span id="signUp" role="button"
                                class="text-primary">{{ __('signup') }}</span></p>

                        <!-- form login section -->
                        <form method="POST" action="{{ route('login') }}" class="mt-5">
                            @csrf
                            <div class="mb-3">
                                <label for="email" class="form-label">{{ __('email') }}</label>
                                <input id="email" type="email" name="email"
                                    class="form-control text-indent shadow-sm bg-grey-light border-0 rounded-pill fw-lighter fs-7 p-3"
                                    placeholder="{{ __('email') }}">
                                @error('email')
                                <span class="text-danger">{{ $message }}</span>
                                @enderror
                            </div>
                            <div class="mb-3">
                                <label for="password" class="form-label">{{ __('password') }}</label>
                                <div class="d-flex position-relative">
                                    <input type="password" id="password" name="password"
                                        class="form-control text-indent auth__password shadow-sm bg-grey-light border-0 rounded-pill fw-lighter fs-7 p-3"
                                        placeholder="{{ __('password') }}">
                                    <span class="password__icon text-primary fs-4 fw-bold bi bi-eye-slash"></span>
                                </div>
                                @error('password')
                                <span class="text-danger">{{ $message }}</span>
                                @enderror
                            </div>
                            <div class="col text-center">
                                <button type="submit"
                                    class="btn btn-outline-dark btn-lg rounded-pill mt-4 w-100">{{ __('login') }}</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div class="d-none d-lg-block col-lg-6 col-xl-6 p-5">
                <div class="row vh-100 p-5">
                    <div class="col align-self-center p-5 text-center">
                        <img src="../public/img/login.png" class="bounce" alt="">
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Register Section -->
    <div class="container register__form">
        <div class="row vh-100 w-100 align-self-center">
            <div class="d-none d-lg-block col-lg-6 col-xl-6 p-5">
                <div class="row vh-100 p-5">
                    <div class="col align-self-center p-5 text-center">
                        <img src="../public/img/register.png" class="bounce" alt="">
                    </div>
                </div>
            </div>
            <div class="col-12 col-lg-6 col-xl-6 px-5">
                <div class="row vh-100">
                    <div class="col align-self-center p-5 w-100">
                        <h3 class="fw-bolder">{{ __('register_here') }}</h3>
                        <p class="fw-lighter fs-6">{{ __('have_account') }} <span id="signIn" role="button"
                                class="text-primary">{{ __('signin') }}</span></p>

                        <!-- form register section -->
                        <form action="{{ route('register') }}" method="POST" class="mt-5">
                            @csrf
                            <div class="mb-3">
                                <label for="name_register" class="form-label_register">{{ __('name') }}</label>
                                <input type="text" name="name_register" id="name_register"
                                    class="form-control text-indent shadow-sm bg-grey-light border-0 rounded-pill fw-lighter fs-7 p-3"
                                    placeholder="{{ __('name') }}">
                                @error('name_register')
                                <span class="text-danger">{{ $message }}</span>
                                @enderror
                            </div>
                            <div class="mb-3">
                                <label for="surname_register" class="form-label">{{ __('surname') }}</label>
                                <input type="text" name="surname_register" id="surname_register"
                                    class="form-control text-indent shadow-sm bg-grey-light border-0 rounded-pill fw-lighter fs-7 p-3"
                                    placeholder="{{ __('surname') }}">
                                @error('surname_register')
                                <span class="text-danger">{{ $message }}</span>
                                @enderror
                            </div>
                            <div class="mb-3">
                                <label for="username_register" class="form-label">{{ __('username') }}</label>
                                <input type="text" name="username_register" id="username_register"
                                    class="form-control text-indent shadow-sm bg-grey-light border-0 rounded-pill fw-lighter fs-7 p-3"
                                    placeholder="{{ __('username') }}">
                                @error('username_register')
                                <span class="text-danger">{{ $message }}</span>
                                @enderror
                            </div>
                            <div class="mb-3">
                                <label for="email_register" class="form-label">{{ __('email') }}</label>
                                <input type="email" name="email_register" id="email_register"
                                    class="form-control text-indent shadow-sm bg-grey-light border-0 rounded-pill fw-lighter fs-7 p-3"
                                    placeholder="{{ __('email') }}">
                                @error('email_register')
                                <span class="text-danger">{{ $message }}</span>
                                @enderror
                            </div>
                            <div class="mb-3">
                                <label for="password_register" class="form-label">{{ __('password') }}</label>
                                <div class="d-flex position-relative">
                                    <input type="password" name="password_register" id="password_register"
                                        class="form-control text-indent auth__password shadow-sm bg-grey-light border-0 rounded-pill fw-lighter fs-7 p-3"
                                        placeholder="{{ __('password') }}">
                                    <span class="password__icon text-primary fs-4 fw-bold bi bi-eye-slash"></span>
                                </div>
                                @error('password_register')
                                <span class="text-danger">{{ $message }}</span>
                                @enderror
                            </div>
                            <div class="mb-3">
                                <label for="password_register_confirmation" class="form-label">{{ __('confirm_password') }}</label>
                                <div class="d-flex position-relative">
                                    <input type="password" name="password_register_confirmation"
                                        id="password_register_confirmation"
                                        class="form-control text-indent auth__password shadow-sm bg-grey-light border-0 rounded-pill fw-lighter fs-7 p-3"
                                        placeholder="{{ __('confirm_password') }}">
                                    <span class="password__icon text-primary fs-4 fw-bold bi bi-eye-slash"></span>
                                </div>
                                @error('password_register_confirmation')
                                <span class="text-danger">{{ $message }}</span>
                                @enderror
                            </div>
                            <div class="col text-center">
                                <button type="submit" class="btn btn-outline-dark btn-lg rounded-pill mt-4 w-100">{{ __('signup') }}</button>
                            </div>
                        </form>

                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="../public/js/script.js"></script>

</body>

</html>