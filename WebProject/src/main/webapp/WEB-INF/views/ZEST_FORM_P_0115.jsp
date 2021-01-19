<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>bootstrap</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <!-- <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous"> -->
    <link rel='stylesheet' href='${pageContext.request.contextPath}/css/zestform.css'  type="text/css" />
    <style>  

    </style>
</head>
<body>
                        <!-- 簡單說明 : class中的 is-valid 和  is-invalid 會影響他要顯示綠色框框還是紅色框框  而下面會顯示的文字 要觀察該格子下方的 div 中的 class 有沒有 valid-feedback 或 invalid-feedback -->
    <div style="height: 15px;"></div>
    <div class="container">
        <div class="row">
            <div class="col-2"></div>
            <div class="col-8 containerHeaderCard">
                <div class="h-100 containerHeaderFontCard">
                    <div class="containerHeaderFontDiv">
                       <span class="containerHeaderFontSpan">自定義輸入</span>
                    </div>
                </div>
            </div>
            <div class="col-2"></div>
        </div>
    </div>
    <div class="container" style="margin-bottom:15px;">
        <div class="row">
            <div class="col-2"></div>
            <div class="col-8 containerBodyCardOutside">
                <form>
                    <div class="form-row containerBodyCard"  style="top:calc(50% - 15px);">
                        <div class="col-md-4">
                          <label for="validationServer01">First name</label>
                        </div>
                        <div class="col-md-8">
                            <input type="text" class="form-control is-valid" id="validationServer01" value="Mark" required>
                            <div class="valid-feedback">
                                Looks good!
                            </div>
                        </div>
                    </div>
                    <div class="form-row containerBodyCard" >
                        <div class="col-md-4">
                            <label for="validationServer02">Last name</label>
                        </div>
                        <div class="col-md-8">
                            <input type="text" class="form-control is-valid" id="validationServer02" value="Otto" required>
                            <div class="valid-feedback">
                                Looks good!
                            </div>
                        </div>
                    </div>

                    <div class="form-row containerBodyCard">
                        <div class="col-md-4 mb-3">
                            <label for="validationServerUsername">Username</label>
                        </div>
                        <div class="col-md-8">
                            <div class="input-group">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="inputGroupPrepend3">@</span>
                                </div>
                                <input type="text" class="form-control is-invalid" id="validationServerUsername" aria-describedby="inputGroupPrepend3" required>
                                <div class="invalid-feedback">
                                    Please choose a username.
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="form-row containerBodyCard">
                        <div class="col-md-4">
                            <label for="validationServer03">City</label>
                        </div>
                        <div class="col-md-8">
                          <input type="text" class="form-control is-invalid" id="validationServer03" required>
                          <div class="invalid-feedback">
                            Please provide a valid city.
                          </div>
                        </div>
                    </div>

                    <div class="form-row containerBodyCard">
                        <div class="col-md-4">
                          <label for="validationServer04">State</label>
                        </div>
                        <div class="col-md-8">
                            <select class="custom-select is-invalid" id="validationServer04" required>
                                <option selected disabled value="">Choose...</option>
                                <option value="">Open this select menu</option>
                                <option value="1">One</option>
                                <option value="2">Two</option>
                                <option value="3">Three</option>
                                <option>...</option>
                            </select>
                            <div class="invalid-feedback">
                                Please select a valid state.
                            </div>
                        </div>
                    </div>
                    <div class="form-row containerBodyCard">
                        <div class="col-md-4">
                            <label for="validationServer05">Zip</label>
                        </div>
                        <div class="col-md-8">
                            <input type="text" class="form-control is-invalid" id="validationServer05" required>
                            <div class="invalid-feedback">
                                Please provide a valid zip.
                            </div>
                        </div>
                    </div>
                    <div class="form-row containerBodyCard" >
                        <div class="col-md-6">
                            <div class="form-check">
                                <input class="form-check-input is-invalid" type="checkbox" value="" id="invalidCheck3" required>
                                <label class="form-check-label" for="invalidCheck3">
                                    Agree to terms and conditions
                                </label>
                                <div class="invalid-feedback">
                                    You must agree before submitting.
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-check">
                                <input class="form-check-input is-invalid" type="checkbox" value="" id="invalidCheck3" required>
                                <label class="form-check-label" for="invalidCheck3">
                                Agree to terms and conditions
                                </label>
                                <div class="invalid-feedback">
                                You must agree before submitting.
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="form-group was-validated containerBodyCard">
                        <select class="custom-select" required>
                            <option value="">Open this select menu</option>
                            <option value="1">One</option>
                            <option value="2">Two</option>
                            <option value="3">Three</option>
                        </select>
                             <div class="invalid-feedback">
                                Example invalid custom select feedback
                            </div>
                    </div>
                    <div class="form-group custom-file was-validated containerBodyCard">
                        <input type="file" class="custom-file-input" id="validatedCustomFile" required>
                        <label class="custom-file-label" for="validatedCustomFile">Choose file...</label>
                        <div class="invalid-feedback">
                            Example invalid custom file feedback
                        </div>
                    </div>

                    <button class="btn btn-primary" type="submit" >Submit form</button>
                </form>
            </div>
        </div>
    </div>
    </body>
    </html>