@vite(['resources/js/app.js'])
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <link rel="icon" href="../../public/img/favicon.png" type="image/x-icon">
    <title>{{ __('login') }} | Tecno Market</title>
</head>

<body>
    <header class="header bg-primary">
        <div class="container px-lg-3">
            <nav class="navbar navbar-expand-lg py-3 px-lg-0">
                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav ms-auto">
                        <li class="nav-item">
                            <a href="{{ route('set_language', ['language' => 'en']) }}" class="nav-link">
                                <img src="{{ asset('img/icon_uk.png') }}" alt="Bandera de Inglesa"
                                    class="me-1 user-icon" style="max-width: 20px;">
                            </a>
                        </li>
                        <li class="nav-item">
                            <a href="{{ route('set_language', ['language' => 'es']) }}" class="nav-link">
                                <img src="{{ asset('img/icon_espana.png') }}" alt="Bandera de España"
                                    class="me-1 user-icon" style="max-width: 20px;">
                            </a>
                        </li>
                    </ul>
                </div>
            </nav>
        </div>
    </header>
    <section class="vh-100 bg-primary">
        <div class="container py-5 h-100">
            <div class="row d-flex justify-content-center align-items-center h-100">
                <div class="col-12 col-md-8 col-lg-6 col-xl-5">
                    <div class="card shadow-2-strong" style="border-radius: 1rem;">
                        <form method="POST" class="card-body p-5 text-center" action="{{ route('adminLoginPost') }}">
                            @csrf
                            <!-- Placeholder for logo -->
                            <img src="../../public/img/logo.png" alt="Logo" class="mb-4"
                                style="width: 100px; height: auto;">

                            <h3 class="mb-5">Admin Panel</h3>
                            @if(Session::has('error_message'))
                            <div class="alert alert-danger" role="alert">
                                <strong>Error:</strong> {{ Session::get('error_message') }}
                            </div>
                            @endif
                            @if(Session::has('success_message'))
                            <div class="alert alert-success" role="alert">
                                <strong>Success:</strong> {{ Session::get('success_message') }}
                            </div>
                            @endif
                            <div class="form-outline mb-4">
                                <input type="text" id="email" class="form-control form-control-lg" name="email" />
                                <label class="form-label" for="email">{{ __('email') }}</label>
                                @error('email')
                                <div class="text-danger">{{ $message }}</div>
                                @enderror
                            </div>

                            <div class="form-outline mb-4">
                                <input type="password" id="password" class="form-control form-control-lg"
                                    name="password" />
                                <label class="form-label" for="password">{{ __('password') }}</label>
                                @error('password')
                                <div class="text-danger">{{ $message }}</div>
                                @enderror
                            </div>

                            <button class="btn btn-primary btn-lg btn-block" type="submit">{{ __('login') }}</button>

                        </form>
                    </div>
                </div>
            </div>
        </div>
    </section>

</body>

</html>