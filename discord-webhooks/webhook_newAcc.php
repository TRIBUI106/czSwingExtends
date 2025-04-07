<?php
// Nhận dữ liệu POST từ webhook
$data = json_decode(file_get_contents('php://input'), true);

// Địa chỉ webhook Discord của bạn
$discord_webhook_url = "";

// Cấu hình dữ liệu cho Discord (Tên và ảnh đại diện)
$discordData = json_encode([
    "username" => "czWebhook", // Tên hiển thị trên Discord
    "avatar_url" => "https://cdn.discordapp.com/attachments/1354525181736911059/1355516818361417778/standard_2.gif?ex=67e936e9&is=67e7e569&hm=c6f9ba391f4d5609c0b67383b544a55dfc8c4048682a103b6da90473bf4d0395&", // Ảnh đại diện
]);

// Lấy thời gian hiện tại
$timestamp = date("c"); // cái này là format zietnam

// Kiểm tra loại webhook và thiết lập tiêu đề cho embed
$embedTitle = "👤 **New Account Created**"; // Tiêu đề với emoji người dùng

// Thêm thông tin người dùng, loại webhook, và thời gian
$username = isset($data['username']) ? $data['username'] : "Unknown User";
$webhookType = isset($data['webhookType']) ? $data['webhookType'] : "Unknown Type";
$sendFrom = ($_SERVER['REMOTE_ADDR'] == '127.0.0.1') ? 'localhost' : "Server | IP : " .$_SERVER['REMOTE_ADDR'];
$email = isset($data['email']) ? $data['email'] : "Unknown Email";
$fullname = isset($data['fullname']) ? $data['fullname'] : "Unknown Name";

// Chuẩn bị nội dung để gửi đến Discord
$messageContent = "Username: **$username**\nEmail: **$email**\nFullname: **$fullname**\nCreated at: **$timestamp**\nWebhook Type: **$webhookType**\nSend From: **$sendFrom**\nDetails: ||" . json_encode($data) . "||";

// Cấu hình embed cho Discord
$embed = [
    'title' => $embedTitle, // Tiêu đề embed
    'description' => $messageContent, // Nội dung message
    'timestamp' => $timestamp, // Thời gian gửi
];

// Kết hợp dữ liệu Discord với embed
$finalMessage = json_decode($discordData, true);
$finalMessage['embeds'] = [$embed];

// Gửi dữ liệu lên Discord webhook
$options = [
    'http' => [
        'method'  => 'POST',
        'header'  => 'Content-type: application/json',
        'content' => json_encode($finalMessage),
    ]
];
$context = stream_context_create($options);
file_get_contents($discord_webhook_url, false, $context);

// Xác nhận đã nhận thành công
echo json_encode(['status' => 'success']);
?>
